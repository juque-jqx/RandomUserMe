package com.julienquievreux.domain.mappers

import com.julienquievreux.domain.interfaces.remote.IContactRemote
import com.julienquievreux.domain.interfaces.view.IContactView

interface ContactMapper {
    fun fromRemoteToView(contactRemote: IContactRemote): IContactView
}