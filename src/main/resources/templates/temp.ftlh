<#macro templ user>
    <#if user.email??>
        <div class="dropdown text-end">
            <a href="/profile">
                <img src="/static/image/avatar.png" alt="mdo"
                     width="32" height="32" class="rounded-circle">
            </a>
        </div>
    <#else>
        <button class="btn btn-light my-2 my-sm-0" type="button"
                onclick="window.location.href = '/login'">
            Войти
        </button>
    </#if>
    <#nested/>
</#macro>