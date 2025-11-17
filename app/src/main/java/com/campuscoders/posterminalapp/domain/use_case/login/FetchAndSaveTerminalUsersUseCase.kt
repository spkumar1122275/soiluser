package com.campuscoders.posterminalapp.domain.use_case.login


import com.campuscoders.posterminalapp.domain.model.TerminalUsers
import com.campuscoders.posterminalapp.domain.repository.locale.LoginRepository
import com.campuscoders.posterminalapp.utils.Resource
import javax.inject.Inject

class FetchAndSaveTerminalUsersUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(accessToken: String, terminalId: String): Resource<Boolean> {
        return try {
            val response = repository.fetchTerminalUsersFromApi(accessToken, terminalId)

            if (response.isSuccessful && response.body() != null) {
                val terminalUsersList = response.body()!!.terminal_users.map { dto ->
                    TerminalUsers(
                        terminalUserTerminalId = dto.terminal_user_terminal_id,
                        terminalUsertaxId = dto.terminal_user_pan,
                        terminalUserStoreId = null,
                        terminalUserFullName = dto.terminal_user_full_name,
                        terminalUserPassword = dto.terminal_user_password,
                        terminalUserDate = dto.terminal_user_date,
                        terminalUserTime = dto.terminal_user_time,
                        canCancelRefund = dto.permissions?.can_cancel_refund,
                        canCollectPayment = dto.permissions?.can_collect_payment,
                        canViewCashiers = dto.permissions?.can_view_cashiers,
                        canAddEditCashiers = dto.permissions?.can_add_edit_cashiers,
                        canDeleteCashiers = dto.permissions?.can_delete_cashiers,
                        canViewProducts = dto.permissions?.can_view_products,
                        canAddEditProducts = dto.permissions?.can_add_edit_products,
                        canDeleteProducts = dto.permissions?.can_delete_products,
                        canViewAllReports = dto.permissions?.can_view_all_reports,
                        canSaveSendReports = dto.permissions?.can_save_send_reports,
                        canManagePos = dto.permissions?.can_manage_pos,
                        terminalUserAdmin = dto.permissions?.is_admin
                    )
                }

                terminalUsersList.forEach { user ->
                    repository.saveTerminalUserToDatabase(user)
                }

                Resource.Success(true)
            } else {
                Resource.Error(false, response.message() ?: "Failed to fetch terminal users")
            }
        } catch (e: Exception) {
            Resource.Error(false, e.localizedMessage ?: "Network or data error")
        }
    }
}
