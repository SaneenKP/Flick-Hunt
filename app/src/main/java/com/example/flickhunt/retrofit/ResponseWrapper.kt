package com.example.flickhunt.retrofit
import com.example.flickhunt.utils.Constants
import com.example.flickhunt.utils.Constants.Companion.Status
import com.example.flickhunt.utils.Constants.Companion.ResponseStatus

/**
 * A simple wrapper class to hold the state of response obtained from api calls.
 */
class ResponseWrapper<out T>(val status : Constants.Companion.Status, val data : T?, val message : String?){

    companion object{
        fun <T> success(data: T): ResponseWrapper<T> = ResponseWrapper(status = Status.SUCCESS , data = data , message = null)
        fun <T> error(message: String): ResponseWrapper<T> = ResponseWrapper(status = Status.ERROR , data = null , message = message)
        fun <T> networkError(message: String): ResponseWrapper<T> = ResponseWrapper(status = Status.NETWORK_ERROR , data = null , message = message)
        fun <T> loading(): ResponseWrapper<T> = ResponseWrapper(status = Status.LOADING , data = null , message = null)
    }

}
sealed class ResponseWrapper2<out T>(val status : Constants.Companion.ResponseStatus, val data : T?, val message : String?){

    class Success<T>(private val responseData : T?) : ResponseWrapper2<T>(status = ResponseStatus.Success , data = responseData , message = "")
    class Error<T>(private val errorMessage : String?) : ResponseWrapper2<T>(status = ResponseStatus.Error, message = errorMessage , data = null)
    class Loading<T>() : ResponseWrapper2<T>(status = ResponseStatus.Loading , data = null , message = "")
    class Empty<T>() : ResponseWrapper2<T>(status = ResponseStatus.Empty , data = null , message = "")
}
