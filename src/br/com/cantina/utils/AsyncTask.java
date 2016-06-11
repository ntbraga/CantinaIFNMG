/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cantina.utils;

/**
 *
 * @author ntbra
 * @param <P> Parameter
 * @param <I> Progress
 * @param <R> Result
 */
public abstract class AsyncTask<P, I, R> implements Runnable{
    private final P params[];
    private final Thread t;
    
    public AsyncTask(P... params){
        this.params = params;
        t = new Thread(this);
    }
    
    @Override
    public final void run() {
        onPreExecute();
        R result = doInBackground(params);
        onPostExecute(result);
    }
    
    protected void onPreExecute(){}
    protected abstract R doInBackground(P... params);
    protected void onPostExecute(R result){}
    protected final void publishProgress(I... values){
        this.onProgressUpdate(values);
    }
    protected void onProgressUpdate(I... values){}
    
    public final Thread start(){
        t.start();
        return t;
    }

    public final Thread getThread() {
        return t;
    }    
}
