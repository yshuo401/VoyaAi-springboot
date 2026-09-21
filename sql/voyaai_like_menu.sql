-- 点赞管理菜单和权限。可重复执行，不会覆盖已有菜单。
START TRANSACTION;

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '点赞管理',menu_id,7,'like','voyaai/like/index','VoyaAiLike','C','0','0','voyaai:like:list','rate','admin',now()
FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M'
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:like:list' AND menu_type='C') LIMIT 1;

SET @like_menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:like:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);

-- 早期版本用了不存在的图标 like，这里只纠正它自己，不影响其他自定义图标。
UPDATE sys_menu SET icon='rate' WHERE perms='voyaai:like:list' AND menu_type='C' AND icon='like';

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '点赞查询',@like_menu,1,'#','F','0','0','voyaai:like:query','admin',now()
WHERE @like_menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:like:query' AND menu_type='F');

INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '点赞删除',@like_menu,2,'#','F','0','0','voyaai:like:remove','admin',now()
WHERE @like_menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:like:remove' AND menu_type='F');

COMMIT;
