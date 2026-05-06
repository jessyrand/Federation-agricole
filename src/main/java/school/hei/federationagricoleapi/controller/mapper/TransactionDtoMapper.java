package school.hei.federationagricoleapi.controller.mapper;

import school.hei.federationagricoleapi.controller.dto.CollectivityTransaction;
import school.hei.federationagricoleapi.controller.dto.PaymentMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.time.LocalDate.now;

@Component
@RequiredArgsConstructor
public class TransactionDtoMapper {
    private final FinancialAccountDtoMapper financialAccountDtoMapper;
    private final MemberDtoMapper memberDtoMapper;

    public CollectivityTransaction mapToDto(school.hei.federationagricoleapi.entity.CollectivityTransaction collectivityTransaction) {
        return CollectivityTransaction.builder()
                .id(collectivityTransaction.getId())
                .amount(collectivityTransaction.getAmount())
                .creationDate(collectivityTransaction.getCreationDate())
                .paymentMode(collectivityTransaction.getPaymentMode() == null ? null : PaymentMode.valueOf(collectivityTransaction.getPaymentMode().name()))
                .accountCredited(financialAccountDtoMapper.mapToDto(collectivityTransaction.getAccountCredited()))
                .memberDebited(memberDtoMapper.mapToDto(collectivityTransaction.getMemberDebited()))
                .build();
    }
}
