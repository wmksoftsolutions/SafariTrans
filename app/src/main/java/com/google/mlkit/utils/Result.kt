package com.google.mlkit.utils

class Result(var status:Int,var data:Any?,var msg:String)

enum class ResultStatus
{
   LOADING,ERROR,SUCCESS,UNAUTORIZED
}
