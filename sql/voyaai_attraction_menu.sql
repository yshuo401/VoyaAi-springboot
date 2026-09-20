-- VoyaAi 基础资料菜单：在现有若依数据库执行，可重复执行。
-- 不创建/删除业务表，不自动给普通角色授权。执行后重新登录。
START TRANSACTION;
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,icon,create_by,create_time)
SELECT '旅游管理',0,5,'voyaai','M','0','0','guide','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M');
SET @voyaai_parent = (SELECT menu_id FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M' ORDER BY menu_id LIMIT 1);

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '景点管理',@voyaai_parent,4,'attraction','voyaai/attraction/index','VoyaAiAttraction','C','0','0','voyaai:attraction:list','list','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:attraction:list' AND menu_type='C');
SET @region_menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:attraction:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '景点查询',@region_menu,1,'#','F','0','0','voyaai:attraction:query','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:attraction:query' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '景点新增',@region_menu,2,'#','F','0','0','voyaai:attraction:add','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:attraction:add' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '景点修改',@region_menu,3,'#','F','0','0','voyaai:attraction:edit','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:attraction:edit' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '景点删除',@region_menu,4,'#','F','0','0','voyaai:attraction:remove','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:attraction:remove' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '景点导出',@region_menu,5,'#','F','0','0','voyaai:attraction:export','admin',now()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:attraction:export' AND menu_type='F');
COMMIT;
