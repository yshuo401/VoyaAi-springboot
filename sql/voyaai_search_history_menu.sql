-- 搜索历史管理菜单和权限。可重复执行，不会覆盖已有菜单。
START TRANSACTION;

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '搜索历史',menu_id,9,'searchHistory','voyaai/searchHistory/index','VoyaAiSearchHistory','C','0','0','voyaai:searchHistory:list','search','admin',now()
FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M'
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:searchHistory:list' AND menu_type='C') LIMIT 1;

SET @menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:searchHistory:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);

UPDATE sys_menu SET menu_name='搜索历史' WHERE perms='voyaai:searchHistory:list' AND menu_type='C' AND menu_name='搜索记录';

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '搜索查询',@menu,1,'#','F','0','0','voyaai:searchHistory:query','admin',now()
WHERE @menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:searchHistory:query' AND menu_type='F');

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '搜索删除',@menu,2,'#','F','0','0','voyaai:searchHistory:remove','admin',now()
WHERE @menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:searchHistory:remove' AND menu_type='F');

COMMIT;
