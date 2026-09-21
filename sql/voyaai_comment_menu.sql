-- 评论管理菜单和权限。可重复执行，不会覆盖已有菜单。
START TRANSACTION;

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '评论管理',menu_id,10,'comment','voyaai/comment/index','VoyaAiComment','C','0','0','voyaai:comment:list','comment','admin',now()
FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M'
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:comment:list' AND menu_type='C') LIMIT 1;

SET @menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:comment:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '评论查询',@menu,1,'#','F','0','0','voyaai:comment:query','admin',now()
WHERE @menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:comment:query' AND menu_type='F');

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '评论删除',@menu,2,'#','F','0','0','voyaai:comment:remove','admin',now()
WHERE @menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:comment:remove' AND menu_type='F');

COMMIT;