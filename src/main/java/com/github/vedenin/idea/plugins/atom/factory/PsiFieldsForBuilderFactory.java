package com.github.vedenin.idea.plugins.atom.factory;

import com.intellij.codeInsight.generation.PsiElementClassMember;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.github.vedenin.idea.plugins.atom.psi.model.PsiFieldsForBuilder;
import com.github.vedenin.idea.plugins.atom.verifier.PsiFieldVerifier;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PsiFieldsForBuilderFactory {

    private final PsiFieldVerifier psiFieldVerifier;

    public PsiFieldsForBuilder createPsiFieldsForBuilder(List<PsiElementClassMember> psiElementClassMembers, PsiClass psiClass) {
        List<PsiField> psiFieldsForSetters = new ArrayList<PsiField>();
        List<PsiField> psiFieldsForConstructor = new ArrayList<PsiField>();
        List<PsiField> allSelectedPsiFields = new ArrayList<PsiField>();
        for (PsiElementClassMember psiElementClassMember : psiElementClassMembers) {
            PsiElement psiElement = psiElementClassMember.getPsiElement();
            if (psiElement instanceof PsiField) {
                allSelectedPsiFields.add((PsiField) psiElement);
                if (psiFieldVerifier.isSetInSetterMethod((PsiField) psiElement, psiClass)) {
                    psiFieldsForSetters.add((PsiField) psiElement);
                } else if (psiFieldVerifier.isSetInConstructor((PsiField) psiElement, psiClass)) {
                    psiFieldsForConstructor.add((PsiField) psiElement);
                }
            }
        }
        return new PsiFieldsForBuilder(psiFieldsForSetters, psiFieldsForConstructor, allSelectedPsiFields);
    }
}
