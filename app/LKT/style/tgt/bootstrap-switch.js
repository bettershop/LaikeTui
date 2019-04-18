/* ========================================================================
 * bootstrap-switch - v3.3.2
 * http://www.bootstrap-switch.org
 * ========================================================================
 * Copyright 2012-2013 Mattia Larentis
 *
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================
 */

(function() {
  var __slice = [].slice;
  $bs=jQuery;
  (function($bs, window) {
    "use strict";
    var BootstrapSwitch;
    BootstrapSwitch = (function() {
      function BootstrapSwitch(elementbs, options) {
        if (options == null) {
          options = {};
        }
        this.$bselementbs = $bs(elementbs);
        this.options = $bs.extend({}, $bs.fn.bootstrapSwitch.defaults, {
          state: this.$bselementbs.is(":checked"),
          size: this.$bselementbs.data("size"),
          animate: this.$bselementbs.data("animate"),
          disabled: this.$bselementbs.is(":disabled"),
          readonly: this.$bselementbs.is("[readonly]"),
          indeterminate: this.$bselementbs.data("indeterminate"),
          inverse: this.$bselementbs.data("inverse"),
          radioAllOff: this.$bselementbs.data("radio-all-off"),
          onColor: this.$bselementbs.data("on-color"),
          offColor: this.$bselementbs.data("off-color"),
          onText: this.$bselementbs.data("on-text"),
          offText: this.$bselementbs.data("off-text"),
          labelText: this.$bselementbs.data("label-text"),
          handleWidth: this.$bselementbs.data("handle-width"),
          labelWidth: this.$bselementbs.data("label-width"),
          baseClass: this.$bselementbs.data("base-class"),
          wrapperClass: this.$bselementbs.data("wrapper-class")
        }, options);
        this.$bswrapper = $bs("<div>", {
          "class": (function(_this) {
            return function() {
              var classes;
              classes = ["" + _this.options.baseClass].concat(_this._getClasses(_this.options.wrapperClass));
              classes.push(_this.options.state ? "" + _this.options.baseClass + "-on" : "" + _this.options.baseClass + "-off");
              if (_this.options.size != null) {
                classes.push("" + _this.options.baseClass + "-" + _this.options.size);
              }
              if (_this.options.disabled) {
                classes.push("" + _this.options.baseClass + "-disabled");
              }
              if (_this.options.readonly) {
                classes.push("" + _this.options.baseClass + "-readonly");
              }
              if (_this.options.indeterminate) {
                classes.push("" + _this.options.baseClass + "-indeterminate");
              }
              if (_this.options.inverse) {
                classes.push("" + _this.options.baseClass + "-inverse");
              }
              if (_this.$bselementbs.attr("id")) {
                classes.push("" + _this.options.baseClass + "-id-" + (_this.$bselementbs.attr("id")));
              }
              return classes.join(" ");
            };
          })(this)()
        });
        this.$bscontainer = $bs("<div>", {
          "class": "" + this.options.baseClass + "-container"
        });
        this.$bson = $bs("<span>", {
          html: this.options.onText,
          "class": "" + this.options.baseClass + "-handle-on " + this.options.baseClass + "-" + this.options.onColor
        });
        this.$bsoff = $bs("<span>", {
          html: this.options.offText,
          "class": "" + this.options.baseClass + "-handle-off " + this.options.baseClass + "-" + this.options.offColor
        });
        this.$bslabel = $bs("<span>", {
          html: this.options.labelText,
          "class": "" + this.options.baseClass + "-label"
        });
        this.$bselementbs.on("init.bootstrapSwitch", (function(_this) {
          return function() {
            return _this.options.onInit.apply(elementbs, arguments);
          };
        })(this));
        this.$bselementbs.on("switchChange.bootstrapSwitch", (function(_this) {
          return function() {
            return _this.options.onSwitchChange.apply(elementbs, arguments);
          };
        })(this));
        this.$bscontainer = this.$bselementbs.wrap(this.$bscontainer).parent();
        this.$bswrapper = this.$bscontainer.wrap(this.$bswrapper).parent();
        this.$bselementbs.before(this.options.inverse ? this.$bsoff : this.$bson).before(this.$bslabel).before(this.options.inverse ? this.$bson : this.$bsoff);
        if (this.options.indeterminate) {
          this.$bselementbs.prop("indeterminate", true);
        }
        this._init();
        this._elementbsHandlers();
        this._handleHandlers();
        this._labelHandlers();
        this._formHandler();
        this._externalLabelHandler();
        this.$bselementbs.trigger("init.bootstrapSwitch");
      }

      BootstrapSwitch.prototype._constructor = BootstrapSwitch;

      BootstrapSwitch.prototype.state = function(value, skip) {
        if (typeof value === "undefined") {
          return this.options.state;
        }
        if (this.options.disabled || this.options.readonly) {
          return this.$bselementbs;
        }
        if (this.options.state && !this.options.radioAllOff && this.$bselementbs.is(":radio")) {
          return this.$bselementbs;
        }
        if (this.options.indeterminate) {
          this.indeterminate(false);
        }
        value = !!value;
        this.$bselementbs.prop("checked", value).trigger("change.bootstrapSwitch", skip);
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.toggleState = function(skip) {
        if (this.options.disabled || this.options.readonly) {
          return this.$bselementbs;
        }
        if (this.options.indeterminate) {
          this.indeterminate(false);
          return this.state(true);
        } else {
          return this.$bselementbs.prop("checked", !this.options.state).trigger("change.bootstrapSwitch", skip);
        }
      };

      BootstrapSwitch.prototype.size = function(value) {
        if (typeof value === "undefined") {
          return this.options.size;
        }
        if (this.options.size != null) {
          this.$bswrapper.removeClass("" + this.options.baseClass + "-" + this.options.size);
        }
        if (value) {
          this.$bswrapper.addClass("" + this.options.baseClass + "-" + value);
        }
        this._width();
        this._containerPosition();
        this.options.size = value;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.animate = function(value) {
        if (typeof value === "undefined") {
          return this.options.animate;
        }
        value = !!value;
        if (value === this.options.animate) {
          return this.$bselementbs;
        }
        return this.toggleAnimate();
      };

      BootstrapSwitch.prototype.toggleAnimate = function() {
        this.options.animate = !this.options.animate;
        this.$bswrapper.toggleClass("" + this.options.baseClass + "-animate");
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.disabled = function(value) {
        if (typeof value === "undefined") {
          return this.options.disabled;
        }
        value = !!value;
        if (value === this.options.disabled) {
          return this.$bselementbs;
        }
        return this.toggleDisabled();
      };

      BootstrapSwitch.prototype.toggleDisabled = function() {
        this.options.disabled = !this.options.disabled;
        this.$bselementbs.prop("disabled", this.options.disabled);
        this.$bswrapper.toggleClass("" + this.options.baseClass + "-disabled");
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.readonly = function(value) {
        if (typeof value === "undefined") {
          return this.options.readonly;
        }
        value = !!value;
        if (value === this.options.readonly) {
          return this.$bselementbs;
        }
        return this.toggleReadonly();
      };

      BootstrapSwitch.prototype.toggleReadonly = function() {
        this.options.readonly = !this.options.readonly;
        this.$bselementbs.prop("readonly", this.options.readonly);
        this.$bswrapper.toggleClass("" + this.options.baseClass + "-readonly");
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.indeterminate = function(value) {
        if (typeof value === "undefined") {
          return this.options.indeterminate;
        }
        value = !!value;
        if (value === this.options.indeterminate) {
          return this.$bselementbs;
        }
        return this.toggleIndeterminate();
      };

      BootstrapSwitch.prototype.toggleIndeterminate = function() {
        this.options.indeterminate = !this.options.indeterminate;
        this.$bselementbs.prop("indeterminate", this.options.indeterminate);
        this.$bswrapper.toggleClass("" + this.options.baseClass + "-indeterminate");
        this._containerPosition();
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.inverse = function(value) {
        if (typeof value === "undefined") {
          return this.options.inverse;
        }
        value = !!value;
        if (value === this.options.inverse) {
          return this.$bselementbs;
        }
        return this.toggleInverse();
      };

      BootstrapSwitch.prototype.toggleInverse = function() {
        var $bsoff, $bson;
        this.$bswrapper.toggleClass("" + this.options.baseClass + "-inverse");
        $bson = this.$bson.clone(true);
        $bsoff = this.$bsoff.clone(true);
        this.$bson.replaceWith($bsoff);
        this.$bsoff.replaceWith($bson);
        this.$bson = $bsoff;
        this.$bsoff = $bson;
        this.options.inverse = !this.options.inverse;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.onColor = function(value) {
        var color;
        color = this.options.onColor;
        if (typeof value === "undefined") {
          return color;
        }
        if (color != null) {
          this.$bson.removeClass("" + this.options.baseClass + "-" + color);
        }
        this.$bson.addClass("" + this.options.baseClass + "-" + value);
        this.options.onColor = value;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.offColor = function(value) {
        var color;
        color = this.options.offColor;
        if (typeof value === "undefined") {
          return color;
        }
        if (color != null) {
          this.$bsoff.removeClass("" + this.options.baseClass + "-" + color);
        }
        this.$bsoff.addClass("" + this.options.baseClass + "-" + value);
        this.options.offColor = value;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.onText = function(value) {
        if (typeof value === "undefined") {
          return this.options.onText;
        }
        this.$bson.html(value);
        this._width();
        this._containerPosition();
        this.options.onText = value;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.offText = function(value) {
        if (typeof value === "undefined") {
          return this.options.offText;
        }
        this.$bsoff.html(value);
        this._width();
        this._containerPosition();
        this.options.offText = value;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.labelText = function(value) {
        if (typeof value === "undefined") {
          return this.options.labelText;
        }
        this.$bslabel.html(value);
        this._width();
        this.options.labelText = value;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.handleWidth = function(value) {
        if (typeof value === "undefined") {
          return this.options.handleWidth;
        }
        this.options.handleWidth = value;
        this._width();
        this._containerPosition();
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.labelWidth = function(value) {
        if (typeof value === "undefined") {
          return this.options.labelWidth;
        }
        this.options.labelWidth = value;
        this._width();
        this._containerPosition();
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.baseClass = function(value) {
        return this.options.baseClass;
      };

      BootstrapSwitch.prototype.wrapperClass = function(value) {
        if (typeof value === "undefined") {
          return this.options.wrapperClass;
        }
        if (!value) {
          value = $bs.fn.bootstrapSwitch.defaults.wrapperClass;
        }
        this.$bswrapper.removeClass(this._getClasses(this.options.wrapperClass).join(" "));
        this.$bswrapper.addClass(this._getClasses(value).join(" "));
        this.options.wrapperClass = value;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.radioAllOff = function(value) {
        if (typeof value === "undefined") {
          return this.options.radioAllOff;
        }
        value = !!value;
        if (value === this.options.radioAllOff) {
          return this.$bselementbs;
        }
        this.options.radioAllOff = value;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.onInit = function(value) {
        if (typeof value === "undefined") {
          return this.options.onInit;
        }
        if (!value) {
          value = $bs.fn.bootstrapSwitch.defaults.onInit;
        }
        this.options.onInit = value;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.onSwitchChange = function(value) {
        if (typeof value === "undefined") {
          return this.options.onSwitchChange;
        }
        if (!value) {
          value = $bs.fn.bootstrapSwitch.defaults.onSwitchChange;
        }
        this.options.onSwitchChange = value;
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype.destroy = function() {
        var $bsform;
        $bsform = this.$bselementbs.closest("form");
        if ($bsform.length) {
          $bsform.off("reset.bootstrapSwitch").removeData("bootstrap-switch");
        }
        this.$bscontainer.children().not(this.$bselementbs).remove();
        this.$bselementbs.unwrap().unwrap().off(".bootstrapSwitch").removeData("bootstrap-switch");
        return this.$bselementbs;
      };

      BootstrapSwitch.prototype._width = function() {
        var $bshandles, handleWidth;
        $bshandles = this.$bson.add(this.$bsoff);
        $bshandles.add(this.$bslabel).css("width", "");
        handleWidth = this.options.handleWidth === "auto" ? Math.max(this.$bson.width(), this.$bsoff.width()) : this.options.handleWidth;
        $bshandles.width(handleWidth);
        this.$bslabel.width((function(_this) {
          return function(index, width) {
            if (_this.options.labelWidth !== "auto") {
              return _this.options.labelWidth;
            }
            if (width < handleWidth) {
              return handleWidth;
            } else {
              return width;
            }
          };
        })(this));
        this._handleWidth = this.$bson.outerWidth();
        this._labelWidth = this.$bslabel.outerWidth();
        this.$bscontainer.width((this._handleWidth * 2) + this._labelWidth);
        return this.$bswrapper.width(this._handleWidth + this._labelWidth);
      };

      BootstrapSwitch.prototype._containerPosition = function(state, callback) {
        if (state == null) {
          state = this.options.state;
        }
        this.$bscontainer.css("margin-left", (function(_this) {
          return function() {
            var values;
            values = [0, "-" + _this._handleWidth + "px"];
            if (_this.options.indeterminate) {
              return "-" + (_this._handleWidth / 2) + "px";
            }
            if (state) {
              if (_this.options.inverse) {
                return values[1];
              } else {
                return values[0];
              }
            } else {
              if (_this.options.inverse) {
                return values[0];
              } else {
                return values[1];
              }
            }
          };
        })(this));
        if (!callback) {
          return;
        }
        return setTimeout(function() {
          return callback();
        }, 50);
      };

      BootstrapSwitch.prototype._init = function() {
        var init, initInterval;
        init = (function(_this) {
          return function() {
            _this._width();
            return _this._containerPosition(null, function() {
              if (_this.options.animate) {
                return _this.$bswrapper.addClass("" + _this.options.baseClass + "-animate");
              }
            });
          };
        })(this);
        if (this.$bswrapper.is(":visible")) {
          return init();
        }
        return initInterval = window.setInterval((function(_this) {
          return function() {
            if (_this.$bswrapper.is(":visible")) {
              init();
              return window.clearInterval(initInterval);
            }
          };
        })(this), 50);
      };

      BootstrapSwitch.prototype._elementbsHandlers = function() {
        return this.$bselementbs.on({
          "change.bootstrapSwitch": (function(_this) {
            return function(e, skip) {
              var state;
              e.preventDefault();
              e.stopImmediatePropagation();
              state = _this.$bselementbs.is(":checked");
              _this._containerPosition(state);
              if (state === _this.options.state) {
                return;
              }
              _this.options.state = state;
              _this.$bswrapper.toggleClass("" + _this.options.baseClass + "-off").toggleClass("" + _this.options.baseClass + "-on");
              if (!skip) {
                if (_this.$bselementbs.is(":radio")) {
                  $bs("[name='" + (_this.$bselementbs.attr('name')) + "']").not(_this.$bselementbs).prop("checked", false).trigger("change.bootstrapSwitch", true);
                }
                return _this.$bselementbs.trigger("switchChange.bootstrapSwitch", [state]);
              }
            };
          })(this),
          "focus.bootstrapSwitch": (function(_this) {
            return function(e) {
              e.preventDefault();
              return _this.$bswrapper.addClass("" + _this.options.baseClass + "-focused");
            };
          })(this),
          "blur.bootstrapSwitch": (function(_this) {
            return function(e) {
              e.preventDefault();
              return _this.$bswrapper.removeClass("" + _this.options.baseClass + "-focused");
            };
          })(this),
          "keydown.bootstrapSwitch": (function(_this) {
            return function(e) {
              if (!e.which || _this.options.disabled || _this.options.readonly) {
                return;
              }
              switch (e.which) {
                case 37:
                  e.preventDefault();
                  e.stopImmediatePropagation();
                  return _this.state(false);
                case 39:
                  e.preventDefault();
                  e.stopImmediatePropagation();
                  return _this.state(true);
              }
            };
          })(this)
        });
      };

      BootstrapSwitch.prototype._handleHandlers = function() {
        this.$bson.on("click.bootstrapSwitch", (function(_this) {
          return function(event) {
            event.preventDefault();
            event.stopPropagation();
            _this.state(false);
            return _this.$bselementbs.trigger("focus.bootstrapSwitch");
          };
        })(this));
        return this.$bsoff.on("click.bootstrapSwitch", (function(_this) {
          return function(event) {
            event.preventDefault();
            event.stopPropagation();
            _this.state(true);
            return _this.$bselementbs.trigger("focus.bootstrapSwitch");
          };
        })(this));
      };

      BootstrapSwitch.prototype._labelHandlers = function() {
        return this.$bslabel.on({
          "mousedown.bootstrapSwitch touchstart.bootstrapSwitch": (function(_this) {
            return function(e) {
              if (_this._dragStart || _this.options.disabled || _this.options.readonly) {
                return;
              }
              e.preventDefault();
              e.stopPropagation();
              _this._dragStart = (e.pageX || e.originalEvent.touches[0].pageX) - parseInt(_this.$bscontainer.css("margin-left"), 10);
              if (_this.options.animate) {
                _this.$bswrapper.removeClass("" + _this.options.baseClass + "-animate");
              }
              return _this.$bselementbs.trigger("focus.bootstrapSwitch");
            };
          })(this),
          "mousemove.bootstrapSwitch touchmove.bootstrapSwitch": (function(_this) {
            return function(e) {
              var difference;
              if (_this._dragStart == null) {
                return;
              }
              e.preventDefault();
              difference = (e.pageX || e.originalEvent.touches[0].pageX) - _this._dragStart;
              if (difference < -_this._handleWidth || difference > 0) {
                return;
              }
              _this._dragEnd = difference;
              return _this.$bscontainer.css("margin-left", "" + _this._dragEnd + "px");
            };
          })(this),
          "mouseup.bootstrapSwitch touchend.bootstrapSwitch": (function(_this) {
            return function(e) {
              var state;
              if (!_this._dragStart) {
                return;
              }
              e.preventDefault();
              if (_this.options.animate) {
                _this.$bswrapper.addClass("" + _this.options.baseClass + "-animate");
              }
              if (_this._dragEnd) {
                state = _this._dragEnd > -(_this._handleWidth / 2);
                _this._dragEnd = false;
                _this.state(_this.options.inverse ? !state : state);
              } else {
                _this.state(!_this.options.state);
              }
              return _this._dragStart = false;
            };
          })(this),
          "mouseleave.bootstrapSwitch": (function(_this) {
            return function(e) {
              return _this.$bslabel.trigger("mouseup.bootstrapSwitch");
            };
          })(this)
        });
      };

      BootstrapSwitch.prototype._externalLabelHandler = function() {
        var $bsexternalLabel;
        $bsexternalLabel = this.$bselementbs.closest("label");
        return $bsexternalLabel.on("click", (function(_this) {
          return function(event) {
            event.preventDefault();
            event.stopImmediatePropagation();
            if (event.target === $bsexternalLabel[0]) {
              return _this.toggleState();
            }
          };
        })(this));
      };

      BootstrapSwitch.prototype._formHandler = function() {
        var $bsform;
        $bsform = this.$bselementbs.closest("form");
        if ($bsform.data("bootstrap-switch")) {
          return;
        }
        return $bsform.on("reset.bootstrapSwitch", function() {
          return window.setTimeout(function() {
            return $bsform.find("input").filter(function() {
              return $bs(this).data("bootstrap-switch");
            }).each(function() {
              return $bs(this).bootstrapSwitch("state", this.checked);
            });
          }, 1);
        }).data("bootstrap-switch", true);
      };

      BootstrapSwitch.prototype._getClasses = function(classes) {
        var c, cls, _i, _len;
        if (!$bs.isArray(classes)) {
          return ["" + this.options.baseClass + "-" + classes];
        }
        cls = [];
        for (_i = 0, _len = classes.length; _i < _len; _i++) {
          c = classes[_i];
          cls.push("" + this.options.baseClass + "-" + c);
        }
        return cls;
      };

      return BootstrapSwitch;

    })();
    $bs.fn.bootstrapSwitch = function() {
      var args, option, ret;
      option = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
      ret = this;
      this.each(function() {
        var $bsthis, data;
        $bsthis = $bs(this);
        data = $bsthis.data("bootstrap-switch");
        if (!data) {
          $bsthis.data("bootstrap-switch", data = new BootstrapSwitch(this, option));
        }
        if (typeof option === "string") {
          return ret = data[option].apply(data, args);
        }
      });
      return ret;
    };
    $bs.fn.bootstrapSwitch.Constructor = BootstrapSwitch;
    return $bs.fn.bootstrapSwitch.defaults = {
      state: true,
      size: null,
      animate: true,
      disabled: false,
      readonly: false,
      indeterminate: false,
      inverse: false,
      radioAllOff: false,
      onColor: "primary",
      offColor: "default",
      onText: "ON",
      offText: "OFF",
      labelText: "&nbsp;",
      handleWidth: "auto",
      labelWidth: "auto",
      baseClass: "bootstrap-switch",
      wrapperClass: "wrapper",
      onInit: function() {},
      onSwitchChange: function() {}
    };
  })(window.jQuery, window);

}).call(this);
