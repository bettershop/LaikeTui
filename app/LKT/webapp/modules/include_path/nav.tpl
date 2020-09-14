<nav class="breadcrumb page_bgcolor">
	{foreach from=$menu item=item key=k name=f1}
		{if $smarty.foreach.f1.first}
			<span class="c-gray en"></span>
			<span  style='color: #414658;'>{$item->title}</span>
		{else}
			<span  class="c-gray en">&gt;</span>
			{if $smarty.foreach.f1.total == 3 && ($smarty.foreach.f1.total-1) == $k}
				<span  style='color: #414658;'>{$item->title}</span>
			{else}
				<a style="margin-top: 10px;"  onclick="javascript :history.back(-1);">{$item->title} </a>
			{/if}
		{/if}
	{/foreach}
</nav>