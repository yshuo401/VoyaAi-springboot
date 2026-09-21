-- 收藏管理菜单和权限。可重复执行，不会覆盖已有菜单。
START TRANSACTION;
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '收藏管理',menu_id,6,'favorite','voyaai/favorite/index','VoyaAiFavorite','C','0','0','voyaai:favorite:list','star','admin',now()
FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M'
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:favorite:list' AND menu_type='C') LIMIT 1;
SET @favorite_menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:favorite:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '收藏查询',@favorite_menu,1,'#','F','0','0','voyaai:favorite:query','admin',now()
WHERE @favorite_menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:favorite:query' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '收藏删除',@favorite_menu,2,'#','F','0','0','voyaai:favorite:remove','admin',now()
WHERE @favorite_menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:favorite:remove' AND menu_type='F');
COMMIT;
