package com.campuscoders.posterminalapp.presentation.cashier_and_daily_report.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.campuscoders.posterminalapp.R
import com.campuscoders.posterminalapp.databinding.FragmentAddCashierBinding
import com.campuscoders.posterminalapp.domain.model.TerminalUsers
import com.campuscoders.posterminalapp.presentation.cashier_and_daily_report.AddCashierViewModel
import com.campuscoders.posterminalapp.utils.Constants
import com.campuscoders.posterminalapp.utils.CustomSharedPreferences
import com.campuscoders.posterminalapp.utils.Resource
import com.campuscoders.posterminalapp.utils.TimeAndDate
import com.campuscoders.posterminalapp.utils.toast

class AddCashierFragment : Fragment() {

    private var _binding: FragmentAddCashierBinding? = null
    private val binding get() = _binding!!

    private var ftransaction: FragmentManager? = null

    private lateinit var viewModel: AddCashierViewModel

    private var getTerminalIdControl = true

    private lateinit var terminalUserFromDb: TerminalUsers

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddCashierBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ftransaction = requireActivity().supportFragmentManager
        viewModel = ViewModelProvider(requireActivity())[AddCashierViewModel::class.java]

        arguments?.let {
            val terminalId = it.getInt("terminal_id")
            viewModel.getTerminalUser(terminalId)
            getTerminalIdControl = false
            binding.buttonSave.text = "Güncelle"
        }

        if (getTerminalIdControl) {
            viewModel.getLastTerminalUsersId()
        }

        binding.buttonCancel.setOnClickListener {
            ftransaction?.popBackStack()
        }
        binding.buttonSave.setOnClickListener {
            if (areTheFieldsNotEmpty() && areThePasswordsMatching()) {
                if (!getTerminalIdControl) viewModel.updateTerminalUser(getUpdatedTerminalUser())
                else viewModel.saveTerminalUser(getTerminalUser())
            }
        }

        observer()
    }

    private fun getTerminalUser(): TerminalUsers {
        val customSharedPreferences = CustomSharedPreferences(requireContext())
        val mainUserInfos = customSharedPreferences.getMainUserLogin(requireContext())
        return TerminalUsers(
            binding.textInputEditTextCashierNo.text.toString(),
            mainUserInfos[requireContext().getString(R.string.user_pan_no)].toString(),
            mainUserInfos[requireContext().getString(R.string.user_store_no)].toString(),
            binding.textInputEditTextCashierNameSurname.text.toString(),
            binding.textInputEditTextPassword.text.toString(),
            TimeAndDate.getLocalDate(Constants.DATE_FORMAT),
            TimeAndDate.getTime(),
            binding.switchIptalIade.isChecked,
            binding.switchTahsilat.isChecked,
            binding.switchKasiyerGoruntuleme.isChecked,
            binding.switchKasiyerEklemeDuzenleme.isChecked,
            binding.switchKasiyerSilme.isChecked,
            binding.switchUrunGoruntuleme.isChecked,
            binding.switchUrunEklemeDuzenleme.isChecked,
            binding.switchUrunSilme.isChecked,
            binding.switchTumRaporlariGoruntuleme.isChecked,
            binding.switchRaporKaydetGonder.isChecked,
            binding.switchPosYonetimi.isChecked,
            binding.switchAdmin.isChecked
        )
    }

    private fun getUpdatedTerminalUser(): TerminalUsers {
        val customSharedPreferences = CustomSharedPreferences(requireContext())
        val mainUserInfos = customSharedPreferences.getMainUserLogin(requireContext())
        return terminalUserFromDb.apply {
            terminalUserTerminalId = binding.textInputEditTextCashierNo.text.toString()
            terminalUsertaxId = mainUserInfos[requireContext().getString(R.string.user_pan_no)].toString()
            terminalUserStoreId = mainUserInfos[requireContext().getString(R.string.user_store_no)].toString()
            terminalUserFullName = binding.textInputEditTextCashierNameSurname.text.toString()
            terminalUserPassword = binding.textInputEditTextPassword.text.toString()
            terminalUserDate = TimeAndDate.getLocalDate(Constants.DATE_FORMAT)
            terminalUserTime = TimeAndDate.getTime()
            canCancelRefund = binding.switchIptalIade.isChecked
            canCollectPayment = binding.switchTahsilat.isChecked
            canViewCashiers = binding.switchKasiyerGoruntuleme.isChecked
            canAddEditCashiers = binding.switchKasiyerEklemeDuzenleme.isChecked
            canDeleteCashiers = binding.switchKasiyerSilme.isChecked
            canViewProducts = binding.switchUrunGoruntuleme.isChecked
            canAddEditProducts = binding.switchUrunEklemeDuzenleme.isChecked
            canDeleteProducts = binding.switchUrunSilme.isChecked
            canViewAllReports = binding.switchTumRaporlariGoruntuleme.isChecked
            canSaveSendReports = binding.switchRaporKaydetGonder.isChecked
            canManagePos = binding.switchPosYonetimi.isChecked
            terminalUserAdmin = binding.switchAdmin.isChecked
        }
    }

    private fun setTerminalUser(terminalUsers: TerminalUsers) {
        binding.textInputEditTextCashierNo.setText(terminalUsers.terminalUserTerminalId)
        binding.textInputEditTextCashierNameSurname.setText(terminalUsers.terminalUserFullName)
        binding.textInputEditTextPassword.setText(terminalUsers.terminalUserPassword)
        binding.textInputEditTextPasswordAgain.setText(terminalUsers.terminalUserPassword)
        binding.switchIptalIade.isChecked = terminalUsers.canCancelRefund!!
        binding.switchTahsilat.isChecked = terminalUsers.canCollectPayment!!
        binding.switchKasiyerGoruntuleme.isChecked = terminalUsers.canViewCashiers!!
        binding.switchKasiyerEklemeDuzenleme.isChecked = terminalUsers.canAddEditCashiers!!
        binding.switchKasiyerSilme.isChecked = terminalUsers.canDeleteCashiers!!
        binding.switchUrunGoruntuleme.isChecked = terminalUsers.canViewProducts!!
        binding.switchUrunEklemeDuzenleme.isChecked = terminalUsers.canAddEditProducts!!
        binding.switchUrunSilme.isChecked = terminalUsers.canDeleteProducts!!
        binding.switchTumRaporlariGoruntuleme.isChecked = terminalUsers.canViewAllReports!!
        binding.switchRaporKaydetGonder.isChecked = terminalUsers.canSaveSendReports!!
        binding.switchPosYonetimi.isChecked = terminalUsers.canManagePos!!
        binding.switchAdmin.isChecked = terminalUsers.terminalUserAdmin!!
    }

    private fun areTheFieldsNotEmpty(): Boolean {
        val cashierNameSurname = binding.textInputEditTextCashierNameSurname.text.toString()
        val cashierPassword = binding.textInputEditTextPassword.text.toString()
        val cashierPasswordAgain = binding.textInputEditTextPasswordAgain.text.toString()

        return if (cashierNameSurname == "" || cashierPassword == "" || cashierPasswordAgain == "") {
            toast(requireContext(), requireActivity().getString(R.string.empty_fields), true)
            false
        } else {
            true
        }
    }

    private fun areThePasswordsMatching(): Boolean {
        val cashierPassword = binding.textInputEditTextPassword.text.toString()
        val cashierPasswordAgain = binding.textInputEditTextPasswordAgain.text.toString()
        return if (cashierPassword != cashierPasswordAgain) {
            toast(requireContext(), "Parolalar Eşleşmiyor", true)
            false
        } else {
            true
        }
    }

    private fun observer() {
        viewModel.statusFetchedTerminalUser.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { terminalUser ->
                        setTerminalUser(terminalUser)
                        terminalUserFromDb = terminalUser
                    }
                    viewModel.resetFetchedTerminalUser()
                }
                is Resource.Loading -> {
                    // loading popup
                }
                is Resource.Error -> {
                    toast(requireContext(), it.message ?: "Error!", false)
                }
                is Resource.Idle -> { /* NO-OP */ }
            }
        }
        viewModel.statusSaveTerminalUser.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    ftransaction?.popBackStack()
                    toast(
                        requireContext(),
                        requireActivity().getString(R.string.success_add_cashier),
                        false
                    )
                    viewModel.resetSaveTerminalUser()
                }
                is Resource.Loading -> {
                    // loading popup
                }
                is Resource.Error -> {
                    toast(requireContext(), it.message ?: "Error!", false)
                }
                is Resource.Idle -> { /* NO-OP */ }
            }
        }
        viewModel.statusLastTerminalUserId.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { terminalId ->
                        binding.textInputEditTextCashierNo.setText("T0000${terminalId + 1}")
                    }
                }
                is Resource.Loading -> {
                    // loading popup
                }
                is Resource.Error -> {
                    toast(requireContext(), it.message ?: "Error!", false)
                }
                is Resource.Idle -> { /* NO-OP */ }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}