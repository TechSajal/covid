package com.example.covid.model

data  class CountryWiseModel (
     val country: String,
     val confirmed: String,
     val newConfirmed: String,
     val active: String,
     val deceased: String,
     val newDeceased: String,
     val recovered: String,
     val tests: String,
     val flag: String
)