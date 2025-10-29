SELECT
    `temp`.`title` AS `title`,
    `temp`.`region_name` AS `region_name`,
    `temp`.`type` AS `type`,
    `temp`.`type_son` AS `type_son`,
    `temp`.`count` AS `count`
FROM
    (
        SELECT
            '工单总览分析' AS `title`,
            `t`.`region_name` AS `region_name`,
            `t`.`status` AS `type`,
            '' AS `type_son`,
            count( 1 ) AS `count`
        FROM
            (
                SELECT
                    `t1`.`id` AS `id`,
                    `t1`.`region_name` AS `region_name`,
                    `t1`.`status` AS `status`
                FROM
                    (
                        SELECT
                            `e`.`id` AS `id`,
                            `r`.`name` AS `region_name`,
                            1 AS `status`
                        FROM
                            (
                                `civilization`.`wf_event_report` `e`
                                    LEFT JOIN `civilization`.`sys_dept` `r` ON ((
                                        `r`.`id` = `e`.`region_code`
                                    )))
                        WHERE
                            ( `e`.`status` <> 70 ) UNION
                        SELECT
                            `civilization`.`work_event`.`id` AS `id`,
                            `civilization`.`work_event`.`region_name` AS `region_name`,
                            1 AS `status`
                        FROM
                            `civilization`.`work_event`
                        WHERE
                            ( `civilization`.`work_event`.`state` <> 3 )) `t1` UNION
                SELECT
                    `t2`.`id` AS `id`,
                    `t2`.`region_name` AS `region_name`,
                    `t2`.`status` AS `status`
                FROM
                    (
                        SELECT
                            `e`.`id` AS `id`,
                            `r`.`name` AS `region_name`,
                            2 AS `status`
                        FROM
                            (
                                `civilization`.`wf_event_report` `e`
                                    LEFT JOIN `civilization`.`sys_dept` `r` ON ((
                                        `r`.`id` = `e`.`region_code`
                                    )))
                        WHERE
                            ( `e`.`status` = 70 ) UNION
                        SELECT
                            `civilization`.`work_event`.`id` AS `id`,
                            `civilization`.`work_event`.`region_name` AS `region_name`,
                            2 AS `status`
                        FROM
                            `civilization`.`work_event`
                        WHERE
                            ( `civilization`.`work_event`.`state` = 3 )) `t2` UNION
                SELECT
                    `t2`.`id` AS `id`,
                    `t2`.`region_name` AS `region_name`,
                    `t2`.`status` AS `status`
                FROM
                    (
                        SELECT
                            `e`.`id` AS `id`,
                            `r`.`name` AS `region_name`,
                            3 AS `status`
                        FROM
                            (
                                `civilization`.`wf_event_report` `e`
                                    LEFT JOIN `civilization`.`sys_dept` `r` ON ((
                                        `r`.`id` = `e`.`region_code`
                                    )))
                        WHERE
                            ( `e`.`extension_flag` = 1 ) UNION
                        SELECT
                            `civilization`.`work_event`.`id` AS `id`,
                            `civilization`.`work_event`.`region_name` AS `region_name`,
                            3 AS `status`
                        FROM
                            `civilization`.`work_event`
                        WHERE
                            ((
                                     `civilization`.`work_event`.`state` = 1
                                 )
                                OR ( `civilization`.`work_event`.`state` = 2 ))) `t2`
            ) `t`
        GROUP BY
            `t`.`region_name`,
            `t`.`status` UNION ALL
        SELECT
            '工单来源分析' AS `title`,
            `cc`.`region_name` AS `region_name`,
            `cc`.`type` AS `type`,
            '' AS `type_son`,
            count( 1 ) AS `count`
        FROM
            (
                SELECT
                    `civilization`.`work_event`.`region_name` AS `region_name`,
                    '1' AS `type`
                FROM
                    `civilization`.`work_event`
                WHERE
                    ( `civilization`.`work_event`.`del_flag` = 0 ) UNION ALL
                SELECT
                    `bb`.`name` AS `region_name`,
                    `aa`.`event_source` AS `type`
                FROM
                    (
                        `civilization`.`wf_event_report` `aa`
                            LEFT JOIN `civilization`.`sys_dept` `bb` ON (((
                                                                                  `bb`.`id` = `aa`.`region_code`
                                                                              )
                            AND ( `aa`.`del_flag` = 0 ))))) `cc`
        GROUP BY
            `cc`.`region_name`,
            `cc`.`type` UNION ALL
        SELECT
            '高发工单类型' AS `title`,
            `dd`.`region_name` AS `region_name`,
            `dd`.`name` AS `type`,
            '' AS `type_son`,
            count( 1 ) AS `count`
        FROM
            (
                SELECT
                    `aa`.`region_name` AS `region_name`,
                    `ii`.event_type_id AS event_type_id,
                    `bb`.`name` AS `name`
                FROM
                    (
                        `civilization`.`work_event` `aa`
                            LEFT JOIN `civilization`.`data_check_item` `ii` ON `ii`.`name` = `aa`.`check_item_name`
                            LEFT JOIN `civilization`.`quota_event_type` `bb` ON `bb`.`id` = `ii`.event_type_id )
                WHERE
                    ((
                         `ii`.event_type_id IS NOT NULL
                         )
                        AND ( `aa`.`del_flag` = 0 )) UNION ALL
                SELECT
                    `bb`.`name` AS `region_name`,
                    `aa`.`event_type` AS event_type_id,
                    `cc`.`name` AS `name`
                FROM
                    ((
                        `civilization`.`wf_event_report` `aa`
                            LEFT JOIN `civilization`.`sys_dept` `bb` ON ((
                                `bb`.`id` = `aa`.`region_code`
                            )))
                        LEFT JOIN `civilization`.`quota_event_type` `cc` ON ((
                            `cc`.`id` = `aa`.`event_type`
                        )))
                WHERE
                    ( `aa`.`del_flag` = 0 )) `dd`
        GROUP BY
            `dd`.`region_name`,
            `dd`.`name` UNION ALL
        SELECT
            '工单趋势分析' AS `title`,
            `cc`.`region_name` AS `region_name`,
            `cc`.`type` AS `type`,
            '' AS `type_son`,
            count( 1 ) AS `count`
        FROM
            (
                SELECT
                    `civilization`.`work_event`.`region_name` AS `region_name`,
                    `civilization`.`work_event`.`check_name` AS `type`
                FROM
                    `civilization`.`work_event`
                WHERE
                    ( `civilization`.`work_event`.`del_flag` = 0 ) UNION ALL
                SELECT
                    `bb`.`name` AS `region_name`,
                    `aa`.`cycle` AS `type`
                FROM
                    (
                        `civilization`.`wf_event_report` `aa`
                            LEFT JOIN `civilization`.`sys_dept` `bb` ON ((
                                `bb`.`id` = `aa`.`region_code`
                            )))
                WHERE
                    ( `aa`.`del_flag` = 0 )) `cc`
        GROUP BY
            `cc`.`region_name`,
            `cc`.`type` UNION ALL
        SELECT
            '工单办结分析' AS `title`,
            `cc`.`region_name` AS `region_name`,
            `cc`.`type` AS `type`,
            `cc`.`type_son` AS `type_son`,
            count( 1 ) AS `count`
        FROM
            (
                SELECT
                    `bb`.`name` AS `region_name`,
                    `aa`.`cycle` AS `type`,
                    '人工办结' AS `type_son`
                FROM
                    (
                        `civilization`.`wf_event_report` `aa`
                            LEFT JOIN `civilization`.`sys_dept` `bb` ON ((
                                `bb`.`id` = `aa`.`region_code`
                            )))
                WHERE
                    ((
                             `aa`.`status` = 70
                         )
                        AND ( `aa`.`del_flag` = 0 )) UNION ALL
                SELECT
                    `civilization`.`work_event`.`region_name` AS `region_name`,
                    `civilization`.`work_event`.`check_name` AS `type`,
                    '人工办结' AS `type_son`
                FROM
                    `civilization`.`work_event`
                WHERE
                    ((
                             `civilization`.`work_event`.`state` = 3
                         )
                        AND ( `civilization`.`work_event`.`del_flag` = 0 ))) `cc`
        GROUP BY
            `cc`.`region_name`,
            `cc`.`type`,
            `cc`.`type_son` UNION ALL
        SELECT
            '高发点位分析' AS `title`,
            `cc`.`region_name` AS `region_name`,
            `cc`.`point_name` AS `type`,
            '' AS `type_son`,
            count( 1 ) AS `count`
        FROM
            (
                SELECT
                    `civilization`.`work_event`.`region_name` AS `region_name`,
                    `civilization`.`work_event`.`point_name` AS `point_name`
                FROM
                    `civilization`.`work_event`
                WHERE
                    ( `civilization`.`work_event`.`del_flag` = 0 ) UNION ALL
                SELECT
                    `cc`.`name` AS `region_name`,
                    `bb`.`name` AS `name`
                FROM
                    ((
                        `civilization`.`wf_event_report` `aa`
                            LEFT JOIN `civilization`.`data_check_points` `bb` ON ((
                                `bb`.`id` = `aa`.`check_points_id`
                            )))
                        LEFT JOIN `civilization`.`sys_dept` `cc` ON ((
                            `cc`.`id` = `aa`.`region_code`
                        )))
                WHERE
                    ( `aa`.`del_flag` = 0 )) `cc`
        GROUP BY
            `cc`.`region_name`,
            `cc`.`point_name` UNION ALL
        SELECT
            '高发区域分析' AS `title`,
            `cc`.`region_name` AS `region_name`,
            '' AS `type`,
            '' AS `type_son`,
            count( 1 ) AS `count`
        FROM
            (
                SELECT
                    `civilization`.`work_event`.`region_name` AS `region_name`
                FROM
                    `civilization`.`work_event`
                WHERE
                    ( `civilization`.`work_event`.`del_flag` = 0 ) UNION ALL
                SELECT
                    `bb`.`name` AS `region_name`
                FROM
                    (
                        `civilization`.`wf_event_report` `aa`
                            LEFT JOIN `civilization`.`sys_dept` `bb` ON (((
                                                                                  `bb`.`id` = `aa`.`region_code`
                                                                              )
                            AND ( `aa`.`del_flag` = 0 ))))) `cc`
        GROUP BY
            `cc`.`region_name` UNION ALL
        SELECT
            '区域同环比分析' AS `title`,
            `cc`.`region_name` AS `region_name`,
            `cc`.`type` AS `type`,
            '' AS `type_son`,
            count( 1 ) AS `count`
        FROM
            (
                SELECT
                    `bb`.`name` AS `region_name`,
                    date_format( `aa`.`create_time`, '%Y-%m' ) AS `type`
                FROM
                    (
                        `civilization`.`wf_event_report` `aa`
                            LEFT JOIN `civilization`.`sys_dept` `bb` ON ((
                                `bb`.`id` = `aa`.`region_code`
                            )))
                WHERE
                    ( `aa`.`del_flag` = 0 ) UNION ALL
                SELECT
                    `civilization`.`work_event`.`region_name` AS `region_name`,
                    date_format( str_to_date( `civilization`.`work_event`.`check_date`, '%Y/%m' ), '%Y-%m' ) AS `type`
                FROM
                    `civilization`.`work_event`
                WHERE
                    ( `civilization`.`work_event`.`del_flag` = 0 )) `cc`
        GROUP BY
            `cc`.`region_name`,
            `cc`.`type` UNION ALL
        SELECT
            '区域办结分析' AS `title`,
            `cc`.`region_name` AS `region_name`,
            `cc`.`type` AS `type`,
            '' AS `type_son`,
            count( 1 ) AS `count`
        FROM
            (
                SELECT
                    `civilization`.`work_event`.`region_name` AS `region_name`,
                    '工单总数' AS `type`
                FROM
                    `civilization`.`work_event`
                WHERE
                    ( `civilization`.`work_event`.`del_flag` = 0 ) UNION ALL
                SELECT
                    `civilization`.`work_event`.`region_name` AS `region_name`,
                    '办结总数' AS `type`
                FROM
                    `civilization`.`work_event`
                WHERE
                    ((
                             `civilization`.`work_event`.`del_flag` = 0
                         )
                        AND ( `civilization`.`work_event`.`state` = 3 )) UNION ALL
                SELECT
                    `bb`.`name` AS `region_name`,
                    '工单总数' AS `type`
                FROM
                    (
                        `civilization`.`wf_event_report` `aa`
                            LEFT JOIN `civilization`.`sys_dept` `bb` ON ((
                                `bb`.`id` = `aa`.`region_code`
                            )))
                WHERE
                    ( `aa`.`del_flag` = 0 ) UNION ALL
                SELECT
                    `bb`.`name` AS `region_name`,
                    '办结总数' AS `type`
                FROM
                    (
                        `civilization`.`wf_event_report` `aa`
                            LEFT JOIN `civilization`.`sys_dept` `bb` ON ((
                                `bb`.`id` = `aa`.`region_code`
                            )))
                WHERE
                    ((
                             `aa`.`del_flag` = 0
                         )
                        AND ( `aa`.`status` = 70 ))) `cc`
        GROUP BY
            `cc`.`region_name`,
            `cc`.`type`
    ) `temp`
ORDER BY
    `temp`.`title`,
    `temp`.`region_name`,
    `temp`.`type`,
    `temp`.`type_son`,
    `temp`.`count`