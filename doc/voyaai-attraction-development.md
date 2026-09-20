# 景点管理开发说明

本次新增景点后台管理，使用已有 `voya_ai_attraction` 表，并新增景点图片表 `voya_ai_attraction_image`。

## 数据库执行顺序

1. 先执行 `sql/voyaai_attraction_image.sql`，脚本使用 `CREATE TABLE IF NOT EXISTS`，不会删除已有景点数据。
2. 再执行 `sql/voyaai_attraction_menu.sql`，增加“旅游管理 → 景点管理”及查询、新增、修改、删除、导出权限。
3. 在角色管理中给账号勾选景点菜单和按钮权限，然后退出重新登录。

## 页面与接口

- 管理页面：`/voyaai/attraction`
- 列表：`GET /voyaai/attraction/list`
- 详情：`GET /voyaai/attraction/{id}`
- 新增、编辑、上下架、批量删除：沿用 `/voyaai/attraction`、`/changeStatus`、`/{ids}`
- 导出：`POST /voyaai/attraction/export`
- 城市下拉：`GET /voyaai/attraction/cityOptions?provinceId=...`

新增和编辑时，城市必须存在且处于启用状态；景点名称在同一城市内不能重复。景点有关联行程项、收藏、点赞或评论时不能删除，可以先下架。

图片仍使用若依的 `/common/upload`。封面保存到景点主表，多图按拖动顺序保存到景点图片表；数据库只保存访问路径，文件目录由 `ruoyi.profile` 决定。

前端源码位于 `RuoYi-Vue3/src/views/voyaai/attraction/index.vue`，接口封装位于 `RuoYi-Vue3/src/api/voyaai/attraction.js`。
