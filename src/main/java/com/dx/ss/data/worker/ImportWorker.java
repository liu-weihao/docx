package com.dx.ss.data.worker;

import com.dx.ss.data.doc.Documentation;

/** 
 * DocumentWorker for data-import
 * @ClassName: ImportWorker 
 * @version V1.0  
 * @date 2016-11-15 
 * @author liu.weihao
 */
public class ImportWorker extends DocumentWorker {

    private Documentation source;
    
    public Documentation getSource() {
        return source;
    }

    public void setSource(Documentation source) {
        this.source = source;
    }

}
