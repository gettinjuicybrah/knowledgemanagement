package com.joeybasile.knowledgemanagement.util
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun generateUUID():String{
    return Uuid.random().toString()
}