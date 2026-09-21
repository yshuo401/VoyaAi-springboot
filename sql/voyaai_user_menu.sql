-- 用户管理菜单和权限。可重复执行，不会覆盖已有菜单。
START TRANSACTION;

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '用户管理',menu_id,12,'user','voyaai/user/index','VoyaAiUser','C','0','0','voyaai:user:list','user','admin',now()
FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M'
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:user:list' AND menu_type='C') LIMIT 1;

SET @menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:user:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '用户查询',@menu,1,'#','F','0','0','voyaai:user:query','admin',now()
WHERE @menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:user:query' AND menu_type='F');

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '用户修改',@menu,2,'#','F','0','0','voyaai:user:edit','admin',now()
WHERE @menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:user:edit' AND menu_type='F');

COMMIT;