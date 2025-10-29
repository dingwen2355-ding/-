package cn.wxgis.jc.common.exception.file;

import cn.wxgis.jc.common.exception.BaseException;

/**
 * 文件信息异常类
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super(code, null, args);
    }

}
