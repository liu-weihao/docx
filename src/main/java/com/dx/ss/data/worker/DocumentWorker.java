package com.dx.ss.data.worker;

/**
 * Document pasing worker in import/export queue.
 * @ClassName: DocumentWorker 
 * @version V1.0  
 * @date 2016-11-14 
 * @author liu.weihao
 */
public abstract class DocumentWorker {
	
	private String workerType;
	
	private String workerStatus;
	
    public String getWorkerType() {
        return workerType;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public String getWorkerStatus() {
        return workerStatus;
    }

    public void setWorkerStatus(String workerStatus) {
        this.workerStatus = workerStatus;
    }

}
