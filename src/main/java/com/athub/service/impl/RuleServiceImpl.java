package com.athub.service.impl;

import com.athub.framework.service.impl.AthubBaseServiceImpl;
import com.athub.mapperdao.RuleMapper;
import com.athub.rules.entity.RuleEntity;
import com.athub.service.RuleService;
import org.springframework.stereotype.Service;

@Service
public class RuleServiceImpl extends AthubBaseServiceImpl<RuleEntity, RuleMapper> implements RuleService {

}
