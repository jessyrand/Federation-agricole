package school.hei.federationagricoleapi.service;

import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.entity.MemberPayment;
import school.hei.federationagricoleapi.entity.Transaction;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.repository.MemberPaymentRepository;
import school.hei.federationagricoleapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.federationagricoleapi.repository.TransactionRepository;

import java.util.List;

import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;
import static school.hei.federationagricoleapi.entity.TransactionType.IN;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberPaymentRepository memberPaymentRepository;
    private final TransactionRepository transactionRepository;

    public List<Member> addNewMembers(List<Member> memberList) {
        for (Member member : memberList) {
            if (!member.refereesAreEligible()) {
                throw new BadRequestException("Member.id=" + member.getId() + " member referees are not eligible");
            }
            if (!member.getMembershipDuesPaid()) {
                throw new BadRequestException("Member.id=" + member.getId() + " membership dues not paid");
            }
            if (!member.getRegistrationFeePaid()) {
                throw new BadRequestException("Member.id=" + member.getId() + " membership fees not paid");
            }
            member.setId(randomUUID().toString());
        }
        return memberRepository.saveAll(memberList);
    }

    public List<MemberPayment> createPayments(List<MemberPayment> memberPaymentList) {
        for (MemberPayment member : memberPaymentList) {
            member.setId(randomUUID().toString());
            member.setCreationDate(now());
        }
        List<MemberPayment> savedMemberPayments = memberPaymentRepository.saveAll(memberPaymentList);

        List<Transaction> newTransactionList = savedMemberPayments.stream()
                .map(memberPayment -> {
                    Transaction transaction = Transaction.builder()
                            .id(randomUUID().toString())
                            .memberDebited(memberPayment.getMemberOwner())
                            .amount(memberPayment.getAmount())
                            .type(IN)
                            .creationDate(memberPayment.getCreationDate())
                            .accountCredited(memberPayment.getAccountCredited())
                            .build();
                    return transaction;
                })
                .toList();

        transactionRepository.saveAll(newTransactionList);

        return savedMemberPayments;
    }
}
