-- 标签管理菜单和权限。可重复执行，不会覆盖已有菜单。
START TRANSACTION;

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '标签管理',menu_id,11,'tag','voyaai/tag/index','VoyaAiTag','C','0','0','voyaai:tag:list','list','admin',now()
FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M'
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:tag:list' AND menu_type='C') LIMIT 1;

SET @menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:tag:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '标签查询',@menu,1,'#','F','0','0','voyaai:tag:query','admin',now()
WHERE @menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:tag:query' AND menu_type='F');

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '标签新增',@menu,2,'#','F','0','0','voyaai:tag:add','admin',now()
WHERE @menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:tag:add' AND menu_type='F');

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '标签修改',@menu,3,'#','F','0','0','voyaai:tag:edit','admin',now()
WHERE @menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:tag:edit' AND menu_type='F');

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '标签删除',@menu,4,'#','F','0','0','voyaai:tag:remove','admin',now()
WHERE @menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:tag:remove' AND menu_type='F');

COMMIT;