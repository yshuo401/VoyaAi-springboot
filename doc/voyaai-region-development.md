# 国家、省份和城市管理

本次后端开发位于 `yang920`，前端继续使用 `master`。使用已存在的
`voya_ai_country`、`voya_ai_province`、`voya_ai_city` 表，不需要重建数据库。

## 启动与菜单配置

1. 在后端根目录执行 `mvn -pl ruoyi-admin -am test`，检查编译和测试。
2. 在 IDEA 中重新加载 Maven，然后重启 `RuoYiApplication`。
3. 在当前若依数据库执行 `sql/voyaai_region_menu.sql`。脚本只补充缺少的菜单和按钮，
   不清空业务表、不自动给普通角色授权。已存在的城市菜单会被复用。
4. 在“系统管理 → 角色管理”给需要的角色勾选目录、菜单和按钮。若依超级管理员使用内置全权限逻辑。
5. 前端运行 `npm run dev`，退出并重新登录，以重新获取 `/getRouters` 和按钮权限。
6. 按“新增国家 → 新增省份 → 新增城市”录入数据。

如果不执行菜单 SQL，也可以在菜单管理中手动配置：

| 名称 | 类型 | 上级 | 路由地址 | 组件路径 | 权限 |
| --- | --- | --- | --- | --- | --- |
| 旅游管理 | 目录 | 主目录 | voyaai | 留空 | 留空 |
| 国家管理 | 菜单 | 旅游管理 | country | voyaai/country/index | voyaai:country:list |
| 省份管理 | 菜单 | 旅游管理 | province | voyaai/province/index | voyaai:province:list |
| 城市管理 | 菜单 | 旅游管理 | city | voyaai/city/index | voyaai:city:list |

每个菜单的按钮权限为对应前缀下的 `query/add/edit/remove`，城市另有 `export`。
组件路径不带 `src/views/` 和 `.vue`。现有菜单路径如有手动修改，脚本不会覆盖，
需要在菜单管理中核对。代码存在不代表菜单已入库，推送 GitHub 也不会替代这一步。

## 接口边界

Entity 只映射持久化字段；Create/Update DTO 只包含允许写入的字段；Query DTO
只包含查询条件和分页；VO 用于详情、列表及城市导出，包含联表查询出的国家、省份名称。
列表直接返回 Mapper 查询得到的 VO 集合，保留 PageHelper 的真实 `total`。

`/voyaai/country`、`/voyaai/province`、`/voyaai/city` 均提供：

| 方法 | 路径后缀 | 用途 |
| --- | --- | --- |
| GET | /list | 分页列表 |
| GET | /{id} | 详情 |
| POST | 无 | 新增 |
| PUT | 无 | 编辑（完整表单） |
| PUT | /changeStatus | 状态切换，仅接收 id、status |
| DELETE | /{ids} | 批量逻辑删除，逗号分隔 ID，最多100条 |

城市额外提供 `POST /voyaai/city/export`，使用相同筛选条件且不分页。
下拉数据来自 `GET /voyaai/country/options` 和
`GET /voyaai/province/options?countryId=1`；默认仅返回有效且启用的地区，
`enabledOnly=false` 用于后台筛选和旧值回显。前端新增、编辑时停用选项不可选。
拥有地区维护或城市维护相应权限的用户可以访问下拉接口，无需额外取得国家修改权限。

列表支持 `name`、`status`、`beginCreateTime`、`endCreateTime`（日期格式 YYYY-MM-DD）；
省份支持 `countryId`；城市支持 `countryId`、`provinceId`。
`pageNum` 从1开始，`pageSize` 默认10、上限100，前端提供10/20/50/100。
结束日期包含当天23:59:59；排序为 `sort DESC, id DESC`，与前端“数值越大越靠前”一致。

城市新增示例（provinceId 必须是实际存在的启用省份）：

```json
{
  "provinceId": 1,
  "name": "成都",
  "coverImage": "/profile/upload/2026/09/20/example.jpg",
  "description": "城市简介",
  "latitude": 30.5728,
  "longitude": 104.0668,
  "sort": 100,
  "status": "0",
  "remark": ""
}
```

修改使用同一组业务字段，增加 `id`；新增不含 `id`。
审计字段由登录用户和数据库时间生成，浏览量和删除标志不接受客户端写入。
系统保持若依的 JSON `code/msg/data` 约定：400参数错误、404不存在、409业务冲突。
这些是响应体业务码，不代表已改造整个若依框架的 HTTP 状态码。

## 业务约束

- 名称必填且最多100字，首尾空格在保存前移除；排序非负；状态仅0或1。
- 经纬度可空，纬度范围[-90,90]、经度范围[-180,180]，最多7位小数。
- 国家编码为空串时保存为 NULL，避免可选编码反复提交空串导致唯一索引冲突。
- 新增、编辑时必须关联有效且启用的上级地区；启用城市时也检查国家、省份。
- 国家有未删除省份、省份有未删除城市时不能删除。
- 城市有关联的未删除景点、攻略或行程时不能删除，批量删除任一项失败则整批不修改。
- 逻辑删除写入 `del_flag='1'`、修改人和修改时间；所有正常列表、详情排除已删除记录。
- 现有数据库唯一索引**包含已删除记录**，因此删除后同名仍被占用，接口会返回409。
  此次没有擅自调整索引、复用旧记录或增加恢复接口。
- Service 在事务内锁定参与修改的地区记录，新增下级时也校验、锁定父级。
  以后实现景点、攻略、行程的写入时，应遵守相同父级锁定协议，避免并发创建引用与删除父级发生竞争。

## 图片与页面

封面继续使用若依 `POST /common/upload`，限制单张。实际目录是
`ruoyi.profile` 加 `/upload/YYYY/MM/DD/文件名`，当前配置对应
`D:/ruoyi/uploadPath/upload/...`，数据库只保存 `/profile/upload/...` 路径。
取消表单或删除城市不自动删除磁盘图片；文件清理不在此次范围内。

前端国家、省份、城市三个入口共用 `src/views/voyaai/components/RegionManager.vue`，
提供列表、筛选、详情、表单、状态选择和删除；城市还有封面上传和导出。
切换国家会清空省份；详情返回的审计字段、浏览量、地区名称不会随保存请求回传。
简介当前使用纯文本，详情按文本显示，避免直接渲染未处理的 HTML。

## 验证与范围

`RegionIntegrationTest` 通过独立的 H2 MySQL 模式数据库加载项目的 MyBatis XML，
测试分页、联表、日期边界、编辑状态、唯一性、上级校验、关联删除和批量原子性。
测试不会连接或清理开发 MySQL 数据。

前端验证命令：`npm run build:prod`。
测试通过后仍应在开发环境重新登录，用已授权角色验证菜单和实际 MySQL 数据。
本次完成后台基础资料；小程序端城市公开查询接口仍留待后续开发。
