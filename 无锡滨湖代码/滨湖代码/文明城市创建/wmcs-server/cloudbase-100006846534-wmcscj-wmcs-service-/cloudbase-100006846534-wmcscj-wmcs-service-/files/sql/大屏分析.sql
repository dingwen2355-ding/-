-- 工单总览分析
select region_name, type, count(1) count from (
    select id, region_name, 1 type from wf_event_report where del_flag = 0 and `status` != 70
    union all
    select id, region_name, 1 type from work_event where del_flag = 0 and state != 3 and state != 1
    union all
    select id, region_name, 2 type from wf_event_report where del_flag = 0 and `status` = 70
    union all
    select id, region_name, 2 type from work_event where del_flag = 0 and (state = 3 or state = 1)
    union all
    select id, region_name, 3 type from wf_event_report where del_flag = 0 and extension_flag = 1
    union all
    select id, region_name, 3 type from work_event where del_flag = 0 and state = 2
) t group by region_name, type

-- 工单来源分析
select region_name, type, count(1) count from (
    select id, region_name, 1 type from work_event where del_flag = 0
    union all
    select id, region_name, event_source type from wf_event_report where del_flag = 0
) t group by region_name, type

-- 高发类型分析
select region_name, type, count(1) count from (
    select e.region_name, i.event_type_id eventType, t.name type from work_event e
    left join data_check_item i on i.name = e.check_item_name left join quota_event_type t on t.id = i.event_type_id where e.del_flag = 0
    union all
    select e.region_name, e.event_type eventType, q.name type from wf_event_report e left join quota_event_type q on q.id = e.event_type where e.del_flag = 0
) t group by region_name, type

-- 工单趋势分析
select region_name, type, name,  count(1) count from (
    select region_name, check_name type, '工单总数' name from work_event where del_flag = 0
    union all
    select region_name, check_name type, '办结总数' name from work_event where del_flag = 0 and state = 3
    union all
    select region_name, cycle, '工单总数' name from wf_event_report where del_flag = 0
    union all
    select region_name, cycle, '办结总数' name from wf_event_report where del_flag = 0 and `status` = 70
) t group by region_name, type, name

-- 工单办结分析
select region_name, type, name, count(1) count from (
    select region_name, check_name type, '人工办结' name from work_event where del_flag = 0 and state = 3
    union all
    select region_name, cycle type, '人工办结' name from wf_event_report where del_flag = 0 and `status` = 70
) t group by region_name, type, name

-- 高发点位分析
select region_name, type, count(1) count from (
    select region_name, point_name type from work_event where del_flag = 0
    union all
    select region_name, check_points type from wf_event_report where del_flag = 0
) t group by type order by count desc

-- 区域同环比分析
select region_name, type, count(1) count from (
    select region_name, DATE_FORMAT(check_date, '%Y-%m') type from work_event where del_flag = 0
    union all
    select region_name, DATE_FORMAT(report_time, '%Y-%m') type from wf_event_report where del_flag = 0
) t group by region_name, type

-- 职能部门同环比分析
select shareholder region_name, type, count(1) as count from (
    select * from (
        SELECT
        a.id, date_format(str_to_date(a.check_date, '%Y/%m'), '%Y-%m') as type, a.state,
        substring_index(substring_index(a.depart_name, ',', b.help_topic_id + 1),',' ,- 1) AS shareholder
        FROM work_event a
        JOIN mysql.help_topic b ON b.help_topic_id <![CDATA[ < ]]> (
        length(a.depart_name) - length(REPLACE (a.depart_name, ',', '')) + 1)
        ) cc where shareholder not in ('蠡园街道','蠡湖街道','马山街道','荣巷街道','胡埭镇','雪浪街道','河埒街道')
    ) dd  where shareholder is not null and shareholder != ''
GROUP BY shareholder, type order by count desc, type, shareholder desc

-- 责任单位整改分析
select region_name, name, type, sum(count) count from (
    select region_name, type, name, count(1) count from (
        select region_name, check_name type, '工单总数' name from work_event where del_flag = 0
        union all
        select region_name, check_name type, '办结总数' name from work_event where del_flag = 0 and state = 3
        union all
        select region_name, cycle type, '工单总数' name from wf_event_report where del_flag = 0
        union all
        select region_name, cycle type, '工单总数' name from wf_event_report where del_flag = 0 and `status` = 70
    ) t group by region_name, type, name
) cc
GROUP BY region_name, name  order by count desc

-- 区域办结分析
select region_name, type, name, count(1) count from (
    select region_name, check_name type, '工单总数' name from work_event where del_flag = 0
    union all
    select region_name, check_name type, '办结总数' name from work_event where del_flag = 0 and state = 3
    union all
    select region_name, cycle type, '工单总数' name from wf_event_report where del_flag = 0
    union all
    select region_name, cycle type, '工单总数' name from wf_event_report where del_flag = 0 and `status` = 70
) t group by region_name, type, name
