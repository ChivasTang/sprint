SELECT
t_file_info.FILE_ID AS FILE_ID
,t_file_info.INST_ID AS INST_ID
,m_instrument.INST_NAME AS INST_NAME
,t_file_info.FILE_NAME AS FILE_NAME
,m_file_type.EXTEND AS FILE_TYPE
,t_file_info.FILE_SIZE AS FILE_SIZE
FROM t_file_info
INNER JOIN m_instrument ON m_instrument.INST_ID=t_file_info.INST_ID
INNER JOIN m_file_type ON m_file_type.FILE_TYPE=t_file_info.FILE_TYPE;