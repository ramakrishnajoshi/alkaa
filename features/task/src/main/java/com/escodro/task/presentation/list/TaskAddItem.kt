package com.escodro.task.presentation.list

import androidx.lifecycle.MutableLiveData
import com.escodro.core.extension.onActionDone
import com.escodro.core.extension.onTextChanged
import com.escodro.task.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import java.util.Objects
import kotlinx.android.synthetic.main.item_add_task.view.*

/**
 * Entry representing an item to add a new task.
 */
internal class TaskAddItem(
    private val onAddClicked: () -> Unit,
    private val onInsertTask: (String) -> Unit,
    private val taskName: MutableLiveData<String>
) : Item() {

    override fun bind(holder: GroupieViewHolder, position: Int) {
        holder.itemView.apply {
            edittext_itemadd_description?.setText(taskName.value)
            edittext_itemadd_description?.onTextChanged { taskName.value = it }
            edittext_itemadd_description.onActionDone { text -> onInsertTask(text) }
            imageview_itemadd_completed.setOnClickListener { onAddClicked() }
        }
    }

    override fun getLayout(): Int = R.layout.item_add_task

    override fun getId(): Long = 0L

    override fun equals(other: Any?): Boolean {
        val otherItem = other as? TaskAddItem ?: return super.equals(other)
        return otherItem.id == id
    }

    override fun hashCode(): Int = Objects.hashCode(id)
}
