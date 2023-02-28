package com.laiketui.domain.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.members.ResolvedField;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.*;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.laiketui.domain.annotation.IgnoreSwaggerField;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.Collections;
import springfox.documentation.schema.Maps;
import springfox.documentation.schema.Types;
import springfox.documentation.schema.property.field.FieldProvider;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.schema.AlternateTypeProvider;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.readers.parameter.ExpansionContext;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeField;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 使用Swagger-ui时文档显示实体类中隐藏部分字段
 */
@Component
@Primary
public class CustomizeModelAttributeParameterExpander extends ModelAttributeParameterExpander {
    private static final Logger LOG = LoggerFactory.getLogger(CustomizeModelAttributeParameterExpander.class);
    private final FieldProvider fieldProvider;

    @Autowired
    protected DocumentationPluginsManager pluginsManager;

    @Autowired
    public CustomizeModelAttributeParameterExpander(FieldProvider fields) {
        super(fields);
        this.fieldProvider = fields;
    }

    public List<Parameter> expand(ExpansionContext context) {
        List<Parameter> parameters = Lists.newArrayList();
        Set<String> beanPropNames = this.getBeanPropertyNames(context.getParamType().getErasedType());
        Iterable<ResolvedField> fields = FluentIterable.from(this.fieldProvider.in(context.getParamType())).filter(this.onlyBeanProperties(beanPropNames));
        AlternateTypeProvider alternateTypeProvider = context.getDocumentationContext().getAlternateTypeProvider();
        FluentIterable<ModelAttributeField> modelAttributes = FluentIterable.from(fields).transform(this.toModelAttributeField(alternateTypeProvider));
        FluentIterable<ModelAttributeField> expendables = modelAttributes.filter(Predicates.not(this.simpleType())).filter(Predicates.not(this.recursiveType(context)));
        Iterator i$ = expendables.iterator();

        while(i$.hasNext()) {
            ModelAttributeField each = (ModelAttributeField)i$.next();
            parameters.addAll(this.expand(context.childContext(this.nestedParentName(context.getParentName(), each.getField()), each.getFieldType(), context.getDocumentationContext())));
        }

        FluentIterable<ModelAttributeField> collectionTypes = modelAttributes.filter(Predicates.and(this.isCollection(), Predicates.not(this.recursiveCollectionItemType(context.getParamType()))));
        i$ = collectionTypes.iterator();

        while(true) {
            while(i$.hasNext()) {
                ModelAttributeField each = (ModelAttributeField)i$.next();
                ResolvedType itemType = Collections.collectionElementType(each.getFieldType());
                if (!Types.isBaseType(itemType) && !itemType.getErasedType().isEnum()) {
                    parameters.addAll(this.expand(context.childContext(this.nestedParentName(context.getParentName(), each.getField()), itemType, context.getDocumentationContext())));
                } else {
                    parameters.add(this.simpleFields(context.getParentName(), context.getDocumentationContext(), each));
                }
            }

            FluentIterable<ModelAttributeField> simpleFields = modelAttributes.filter(this.simpleType());
            i$ = simpleFields.iterator();

            while(i$.hasNext()) {
                ModelAttributeField each = (ModelAttributeField)i$.next();
                parameters.add(this.simpleFields(context.getParentName(), context.getDocumentationContext(), each));
            }

            return FluentIterable.from(parameters).filter(Predicates.not(this.hiddenParameters())).toList();
        }
    }

    private Predicate<ModelAttributeField> recursiveCollectionItemType(final ResolvedType paramType) {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return Objects.equal(Collections.collectionElementType(input.getFieldType()), paramType);
            }
        };
    }

    private Predicate<Parameter> hiddenParameters() {
        return new Predicate<Parameter>() {
            @Override
            public boolean apply(Parameter input) {
                return input.isHidden();
            }
        };
    }

    private Parameter simpleFields(String parentName, DocumentationContext documentationContext, ModelAttributeField each) {
        String dataTypeName = (String)Optional.fromNullable(Types.typeNameFor(each.getFieldType().getErasedType())).or(each.getFieldType().getErasedType().getSimpleName());
        ParameterExpansionContext parameterExpansionContext = new ParameterExpansionContext(dataTypeName, parentName, each.getField(), documentationContext.getDocumentationType(), new ParameterBuilder());
        return this.pluginsManager.expandParameter(parameterExpansionContext);
    }

    private Predicate<ModelAttributeField> recursiveType(final ExpansionContext context) {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return context.hasSeenType(input.getFieldType());
            }
        };
    }

    private Predicate<ModelAttributeField> simpleType() {
        return Predicates.and(new Predicate[]{Predicates.not(this.isCollection()), Predicates.not(this.isMap()), Predicates.or(new Predicate[]{this.belongsToJavaPackage(), this.isBaseType(), this.isEnum()})});
    }

    private Predicate<ModelAttributeField> isCollection() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return Collections.isContainerType(input.getFieldType());
            }
        };
    }

    private Predicate<ModelAttributeField> isMap() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return Maps.isMapType(input.getFieldType());
            }
        };
    }

    private Predicate<ModelAttributeField> isEnum() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return input.getFieldType().getErasedType().isEnum();
            }
        };
    }

    private Predicate<ModelAttributeField> belongsToJavaPackage() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return CustomizeModelAttributeParameterExpander.this.packageName(input.getFieldType().getErasedType()).startsWith("java.lang");
            }
        };
    }

    private Predicate<ModelAttributeField> isBaseType() {
        return new Predicate<ModelAttributeField>() {
            @Override
            public boolean apply(ModelAttributeField input) {
                return Types.isBaseType(input.getFieldType()) || input.getField().getType().isPrimitive();
            }
        };
    }

    private Function<ResolvedField, ModelAttributeField> toModelAttributeField(final AlternateTypeProvider alternateTypeProvider) {
        return new Function<ResolvedField, ModelAttributeField>() {
            @Override
            public ModelAttributeField apply(ResolvedField input) {
                return new ModelAttributeField(CustomizeModelAttributeParameterExpander.this.fieldType(alternateTypeProvider, input), input);
            }
        };
    }

    private Predicate<ResolvedField> onlyBeanProperties(final Set<String> beanPropNames) {
        return new Predicate<ResolvedField>() {
            @Override
            public boolean apply(ResolvedField input) {
                return beanPropNames.contains(input.getName());
            }
        };
    }

    private String nestedParentName(String parentName, ResolvedField field) {
        String name = field.getName();
        ResolvedType fieldType = field.getType();
        if (Collections.isContainerType(fieldType) && !Types.isBaseType(Collections.collectionElementType(fieldType))) {
            name = name + "[0]";
        }

        return Strings.isNullOrEmpty(parentName) ? name : String.format("%s.%s", parentName, name);
    }

    private ResolvedType fieldType(AlternateTypeProvider alternateTypeProvider, ResolvedField field) {
        return alternateTypeProvider.alternateFor(field.getType());
    }

    private String packageName(Class<?> type) {
        return (String) Optional.fromNullable(type.getPackage()).transform(this.toPackageName()).or("java");
    }

    private Function<Package, String> toPackageName() {
        return new Function<Package, String>() {
            public String apply(Package input) {
                return input.getName();
            }
        };
    }

    private Set<String> getBeanPropertyNames(Class<?> clazz) {
        try {
            Set<String> beanProps = new HashSet();
            PropertyDescriptor[] propDescriptors = this.getBeanInfo(clazz).getPropertyDescriptors();
            PropertyDescriptor[] arr$ = propDescriptors;
            int len$ = propDescriptors.length;
            for(int i$ = 0; i$ < len$; ++i$) {
                PropertyDescriptor propDescriptor = arr$[i$];
                //增加忽略逻辑 忽略IgnoreSwaggerParameter注解字段
                Field field = FieldUtils.getDeclaredField(clazz, propDescriptor.getName(), true);
                if (field!=null) {
                    field.setAccessible(true);
                    IgnoreSwaggerField ignoreSwaggerParameter = field.getDeclaredAnnotation(IgnoreSwaggerField.class);
                    if (ignoreSwaggerParameter != null) {
                        continue;
                    }
                }
                // 增加结束
                if (propDescriptor.getReadMethod() != null) {
                    beanProps.add(propDescriptor.getName());
                }
            }
            return beanProps;
        } catch (IntrospectionException var8) {
            LOG.warn(String.format("Failed to get bean properties on (%s)", clazz), var8);
            return Sets.newHashSet();
        }
    }

    @VisibleForTesting
    BeanInfo getBeanInfo(Class<?> clazz) throws IntrospectionException {
        return Introspector.getBeanInfo(clazz);
    }
}