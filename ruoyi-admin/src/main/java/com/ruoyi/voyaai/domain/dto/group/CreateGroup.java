package com.ruoyi.voyaai.domain.dto.group;

import jakarta.validation.groups.Default;

/**
 * Validation group for create operations. Extends Default so that ungrouped constraints are also validated.
 */
public interface CreateGroup extends Default {
}