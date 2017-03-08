package com.dx.ss.data.factory;

import com.dx.ss.data.worker.DocumentWorker;
import com.dx.ss.data.worker.ExportWorker;

public class ExportWorkerFactory implements DocumentWorkerFactory {

    public DocumentWorker createWorker() {
        
        return new ExportWorker();
    }

}
