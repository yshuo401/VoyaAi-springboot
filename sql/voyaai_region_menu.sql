-- VoyaAi 基础资料菜单：在现有若依数据库执行，可重复执行。
-- 不创建/删除业务表，不自动给普通角色授权。执行后重新登录。
START TRANSACTION;
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,icon,create_by,create_time)
SELECT '旅游管理',0,5,'voyaai','M','0','0','guide','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M');
SET @voyaai_parent = (SELECT menu_id FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M' ORDER BY menu_id LIMIT 1);

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '国家管理',@voyaai_parent,1,'country','voyaai/country/index','VoyaAiCountry','C','0','0','voyaai:country:list','list','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:country:list' AND menu_type='C');
SET @region_menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:country:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '国家查询',@region_menu,1,'#','F','0','0','voyaai:country:query','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:country:query' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '国家新增',@region_menu,2,'#','F','0','0','voyaai:country:add','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:country:add' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '国家修改',@region_menu,3,'#','F','0','0','voyaai:country:edit','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:country:edit' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '国家删除',@region_menu,4,'#','F','0','0','voyaai:country:remove','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:country:remove' AND menu_type='F');

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '省份管理',@voyaai_parent,2,'province','voyaai/province/index','VoyaAiProvince','C','0','0','voyaai:province:list','list','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:province:list' AND menu_type='C');
SET @region_menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:province:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '省份查询',@region_menu,1,'#','F','0','0','voyaai:province:query','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:province:query' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '省份新增',@region_menu,2,'#','F','0','0','voyaai:province:add','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:province:add' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '省份修改',@region_menu,3,'#','F','0','0','voyaai:province:edit','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:province:edit' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '省份删除',@region_menu,4,'#','F','0','0','voyaai:province:remove','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:province:remove' AND menu_type='F');

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '城市管理',@voyaai_parent,3,'city','voyaai/city/index','VoyaAiCity','C','0','0','voyaai:city:list','list','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:city:list' AND menu_type='C');
SET @region_menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:city:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '城市查询',@region_menu,1,'#','F','0','0','voyaai:city:query','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:city:query' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '城市新增',@region_menu,2,'#','F','0','0','voyaai:city:add','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:city:add' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '城市修改',@region_menu,3,'#','F','0','0','voyaai:city:edit','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:city:edit' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '城市删除',@region_menu,4,'#','F','0','0','voyaai:city:remove','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:city:remove' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '城市导出',@region_menu,5,'#','F','0','0','voyaai:city:export','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:city:export' AND menu_type='F');
COMMIT;
