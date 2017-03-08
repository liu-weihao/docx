package com.dx.ss.data.enums;

/**
 * Worker status definitions. Since document parsing work is multi-thread.
 * @ClassName: WorkerStatusEnums 
 * @version V1.0  
 * @date 2016-10-21 
 * @author liu.weihao
 * @see com.tsou.community.data.worker.DocumentWorker
 */
public enum WorkerStatusEnums {
	
    RUNNING(1, "ST: Running."),
    SHUTDOWN(0, "ST: Shutdown."),
    STOP(2, "ST: Stop."),
    TIDYING(3, "ST: Tidying."),
    TERMINATED(4, "ST: Terminated.");

    private int status;

    private String detail;

    WorkerStatusEnums(int status, String detail) {
        this.status = status;
        this.detail = detail;
    }

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public static WorkerStatusEnums getEnumByStatus(int status) {
        for (WorkerStatusEnums statusEnum : WorkerStatusEnums.values()) {
            if (status == statusEnum.getStatus()) {
                return statusEnum;
            }
        }
        return null;
    }
	
}
