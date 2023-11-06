package com.zakharau.financial_transactions.service.financialOperation;

import com.zakharau.financial_transactions.entity.FinancialOperation;
import com.zakharau.financial_transactions.model.CreateFinancialOperationModel;
import com.zakharau.financial_transactions.model.FinancialOperationModel;
import com.zakharau.financial_transactions.repository.FinancialOperationRepo;
import com.zakharau.financial_transactions.util.FinancialOperationMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinancialOperationServiceImpl implements FinancialOperationService {

  private final FinancialOperationRepo repo;
  private final FinancialOperationMapper mapper;

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public FinancialOperationModel save(CreateFinancialOperationModel model) {

    FinancialOperation financialOperation = mapper.fromCreateFinancialOperationModel(model);
    return mapper.fromFinancialOperation(repo.save(financialOperation));
  }

  @Transactional(readOnly = true)
  @Override
  public List<FinancialOperationModel> getAll() {

    List<FinancialOperation> operationList = repo.findAll();
    return operationList.stream().map(mapper::fromFinancialOperation).toList();
  }

  @Transactional
  @Override
  public FinancialOperationModel update(FinancialOperationModel model) {

    FinancialOperation financialOperation = mapper.fromFinancialOperationModel(model);
    if (getFinancialOperationById(model.getId()).isPresent()) {
      return mapper.fromFinancialOperation(repo.saveAndFlush(financialOperation));
    }else {
      throw new EntityNotFoundException("This financial operation does not exist");
    }

  }

  @Transactional
  @Override
  public void delete(long id) {

    Optional<FinancialOperation> financialOperation = getFinancialOperationById(id);
    if (financialOperation.isPresent()) {
      repo.delete(financialOperation.get());
    } else {
      throw new EntityNotFoundException(
          String.format("Financial operation with id %s does not exist", id));
    }
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<FinancialOperation> getFinancialOperationById(long id) {

    return repo.getFinancialOperationById(id);
  }
}
