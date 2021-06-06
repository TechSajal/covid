package com.example.covid.model

data  class DistrictWiseModel (
    val district:String,
    val confirmed:String,
    val active:String,
    val recovered:String,
    val deceased:String,
    val newconfirmed:String,
    val newrecovered:String,
    val newdeceades:String
    )