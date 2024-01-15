package com.julienquievreux.randomuser.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.julienquievreux.randomuser.models.ContactView

class ContactDiffCallback : DiffUtil.ItemCallback<ContactView>() {
    override fun areItemsTheSame(oldItem: ContactView, newItem: ContactView): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: ContactView, newItem: ContactView): Boolean {
        return oldItem == newItem
    }
}