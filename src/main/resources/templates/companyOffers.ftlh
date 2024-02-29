<#import "temp.ftlh" as t>
<@t.templ user>
<h1>Company offer</h1>
    <form action="/companyOffers" method="get">
        <div class="form-group col-md-10">
            <label for="searchWord">Поиск по объявлениям</label>
            <input type="text" name="searchWord" <#if searchWord??>value="${searchWord}"</#if> class="form-control"
                   id="searchWord">
        </div>
        <div>
            <input type="checkbox" id="scales" name="SearchCate" value="IT"/>
            <label for="scales">IT</label>
        </div>
        <div>
            <input type="checkbox" id="" name="SearchCate" value="Hey"/>
            <label for="scales">IT</label>
        </div>
        <button type="submit">Find</button>
    </form>

<ul>
    <#list companyOffer as cf>
        <li><a href="/companyOffer/${cf.id}">${cf.title}</a></li>
        <img width="50px" height="50px" src="/imageCompany/${cf.previewImageId}">

        <#else>
        <h1 style="text-align: center">Не найдено</h1>
    </#list>
</ul>
<form action="/companyOffer/create" method="post" enctype="multipart/form-data">
    <div>
        <label for="title">Title</label>
        <input type="text" id="title" name="title" placeholder="Title"></br>
    </div>
    <label for="location">location</label>
    <input type="text" id="location" name="location">
    <label for="short_desc">short description</label>
    <input type="text" id="short_desc" name="short_desc">
    <label for="tg">telegram link</label>
    <input type="text" id="tg" name="telegram_link">
    <label for="ll">linkedin link</label>
    <input type="text" id="ll" name="linkedin_link">
    <label for="fl">facebook link</label>
    <input type="text" id="fl" name="facebook_link">
    <label for="pd">project description</label>
    <div>
        <input type="checkbox" id="scales" name="categories" value="IT"/>
        <label for="scales">IT</label>
    </div>
    <div>
        <input type="checkbox" id="" name="categories" value="Hey"/>
        <label for="scales">IT</label>
    </div>
    <div>
        <input type="checkbox" id="" name="" value="It"/>
        <label for="scales">IT</label>
    </div>
    <div>
        <input type="checkbox" id="s" name="" value="Its"/>
        <label for="scales">IT</label>
    </div>
    <input type="text" id="pd" name="project_desc">
    <div class="custom-file">
        <input type="file" name="file1" id="file1">
        <label class="custom-file-label" for="file1">Background photo</label>
    </div>
    <div class="custom-file">
        <input required type="file" name="file2" id="file2">
        <label class="custom-file-label" for="file2">Main photo</label>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit">Добавить</button>
</form>
</@t.templ>