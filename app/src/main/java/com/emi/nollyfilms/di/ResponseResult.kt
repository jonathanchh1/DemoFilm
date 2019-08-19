package com.emi.nollyfilms.di

 class ResponseResult<T> constructor(var status : Status?,  var result : T?,  var exception : Throwable?){


    enum class Status{
        SUCCESSFUL,
        ERROR
    }

     companion object {
         fun <T> success(result: T): ResponseResult<T> {
             return ResponseResult(Status.SUCCESSFUL, result, null)
         }

         fun <T> error(error: T, msg: Throwable?): ResponseResult<T> {
             return ResponseResult(Status.ERROR, error, msg)
         }
     }
}