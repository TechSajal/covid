package com.example.covid.model

data class StateWiseModel (
           val state:String,
           val confirmed:String,
           val confirmed_new:String,
           val active:String,
           val death:String,
           val death_new:String,
           val recovered:String,
           val recovered_new:String,
           val lastupdate:String
        )