package com.example.locmart.data.store

import javax.inject.Inject

class SearchStore @Inject constructor() : BaseStore<Array<String>>("searches" , Array<String>::class.java)