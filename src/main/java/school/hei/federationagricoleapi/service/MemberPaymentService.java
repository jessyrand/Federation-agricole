package school.hei.federationagricoleapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.federationagricoleapi.entity.Account;
import school.hei.federationagricoleapi.entity.DTO.CreateMemberPaymentDTO;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.entity.MemberPayment;
import school.hei.federationagricoleapi.entity.MembershipFee;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberPaymentService {

    private MemberRepository memberRepository;
    private MembershipFeeRepository membershipFeeRepository;
    private AccountRepository accountRepository;
    private CollectivityTransactionRepository collectivityTransactionRepository;
    private MemberPaymentRepository memberPaymentRepository;

    public List<MemberPayment> createPayments(String memberId, List<CreateMemberPaymentDTO> dtos) {

        Optional<Member> member = memberRepository.findById(memberId);
        if(member == null){
            throw new NotFoundException("Member not found");
        };

        String collectivityId = memberRepository.findCollectivityIdByMemberId(memberId);

        List<MemberPayment> payments = new ArrayList<>();

        for (CreateMemberPaymentDTO dto : dtos) {

            if (dto.getAmount() == null || dto.getAmount() <= 0) {
                throw new BadRequestException("Amount must be positive");
            }

            Optional<MembershipFee> membershipFee = membershipFeeRepository.findById(dto.getMembershipFeeIdentifier());
            if (membershipFee == null){
                throw new NotFoundException("Membership fee not found");
            };

            Account accountCredited = accountRepository.findAccountById(dto.getAccountCreditedIdentifier());
            if(accountCredited == null){
                new NotFoundException("Account not found");
            }

            MemberPayment payment = memberPaymentRepository.save(memberId, dto);

            collectivityTransactionRepository.save(
                    collectivityId,
                    memberId,
                    dto.getAmount(),
                    dto.getPaymentMode(),
                    dto.getAccountCreditedIdentifier()
            );

            payments.add(payment);
        }

        return payments;
    }
}
