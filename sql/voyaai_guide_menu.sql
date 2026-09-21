-- 攻略管理菜单和权限。可重复执行，不会覆盖已有菜单。
START TRANSACTION;
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,component,route_name,menu_type,visible,status,perms,icon,create_by,create_time)
SELECT '攻略管理',menu_id,5,'guide','voyaai/guide/index','VoyaAiGuide','C','0','0','voyaai:guide:list','reading','admin',now()
FROM sys_menu WHERE parent_id=0 AND path='voyaai' AND menu_type='M'
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:guide:list' AND menu_type='C') LIMIT 1;
SET @guide_menu = (SELECT menu_id FROM sys_menu WHERE perms='voyaai:guide:list' AND menu_type='C' ORDER BY menu_id LIMIT 1);
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '攻略查询',@guide_menu,1,'#','F','0','0','voyaai:guide:query','admin',now()
WHERE @guide_menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:guide:query' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '攻略新增',@guide_menu,2,'#','F','0','0','voyaai:guide:add','admin',now()
WHERE @guide_menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:guide:add' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '攻略修改',@guide_menu,3,'#','F','0','0','voyaai:guide:edit','admin',now()
WHERE @guide_menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:guide:edit' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '攻略删除',@guide_menu,4,'#','F','0','0','voyaai:guide:remove','admin',now()
WHERE @guide_menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:guide:remove' AND menu_type='F');
INSERT INTO sys_menu (menu_name,parent_id,order_num,path,menu_type,visible,status,perms,create_by,create_time)
SELECT '攻略导出',@guide_menu,5,'#','F','0','0','voyaai:guide:export','admin',now()
WHERE @guide_menu IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='voyaai:guide:export' AND menu_type='F');
COMMIT;
