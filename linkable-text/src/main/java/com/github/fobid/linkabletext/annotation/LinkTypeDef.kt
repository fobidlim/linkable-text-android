package com.github.fobid.linkabletext.annotation

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class LinkTypeDef(vararg val value: Int)