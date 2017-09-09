package com.dx.ss.data.enums;

import org.apache.commons.lang.StringUtils;

/**
 * <p class="detail">
 * Worker type definitions, just import/export.
 * </p>
 * @ClassName: WorkerTypeEnums 
 * @version V1.0  
 * @date 2016-10-21 
 * @author liu.weihao
 * @see com.dx.ss.data.worker.DocumentWorker
 */
public enum WorkerTypeEnums {
	
	IMPORT_WORKER("import", "import worker type."),
	EXPORT_WORKER("export", "export worker type.");

    private String type;

    private String descriptions;

    WorkerTypeEnums(String type, String descriptions) {
        this.type = type;
        this.descriptions = descriptions;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public static WorkerTypeEnums getEnumByType(String type) {
    	if (!StringUtils.isBlank(type)) {
			for (WorkerTypeEnums typeEnum : WorkerTypeEnums.values()) {
				if (type.equals(typeEnum.getType())) {
					return typeEnum;
				}
			}
		}
        return null;
    }
	
}
