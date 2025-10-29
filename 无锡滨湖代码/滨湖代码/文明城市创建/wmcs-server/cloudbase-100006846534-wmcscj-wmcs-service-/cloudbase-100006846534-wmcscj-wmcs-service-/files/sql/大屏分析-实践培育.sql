select type, count(1) count from (
    select '新时代好少年' type from data_civilization_advanced where del_flag = 0 and recommend_type = 1
    union
    select '身边好人' type from data_civilization_advanced where del_flag = 0 and recommend_type = 2
    union
    select '最美人物' type from data_civilization_advanced where del_flag = 0 and recommend_type = 3
    union
    select '岗位学雷锋标兵' type  from data_civilization_advanced where del_flag = 0 and recommend_type = 4
) t group by type