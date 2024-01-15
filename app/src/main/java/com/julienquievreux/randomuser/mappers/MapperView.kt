package com.julienquievreux.randomuser.mappers

import com.julienquievreux.domain.interfaces.view.IContactView
import com.julienquievreux.randomuser.models.ContactView

fun IContactView.toContactView(): ContactView= ContactView(
    uuid = this.uuid,
    username = this.username,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    gender = this.gender,
    registrationDate = this.registrationDate,
    registrationAge = this.registrationAge,
    phoneNumber = this.phoneNumber,
    largePicUrl = this.largePicUrl,
    mediumPicUrl = this.mediumPicUrl,
    thumbPicUrl = this.thumbPicUrl,
    latitude = this.latitude,
    longitude = this.longitude
)