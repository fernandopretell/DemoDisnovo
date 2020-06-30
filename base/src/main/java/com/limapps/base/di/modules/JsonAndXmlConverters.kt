package com.limapps.base.di.modules

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

object JsonAndXmlConverters {

    @Retention(AnnotationRetention.RUNTIME)
    annotation class Json

    @Retention(AnnotationRetention.RUNTIME)
    annotation class Xml

    class QualifiedTypeConverterFactory(private val jsonFactory: Converter.Factory, private val xmlFactory: Converter.Factory) : Converter.Factory() {

        override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>,
                                           retrofit: Retrofit?): Converter<ResponseBody, *>? {
            return if (annotations.any { it is Xml }) xmlFactory.responseBodyConverter(type, annotations, retrofit) else jsonFactory.responseBodyConverter(type, annotations, retrofit)
        }

        override fun requestBodyConverter(type: Type?,
                                          parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
            return if (parameterAnnotations.any { it is Xml }) xmlFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations,
                    retrofit) else jsonFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations,
                    retrofit)
        }
    }
}