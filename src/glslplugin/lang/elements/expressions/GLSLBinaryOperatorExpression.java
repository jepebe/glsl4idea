/*
 *     Copyright 2010 Jean-Paul Balabanian and Yngve Devik Hammersland
 *
 *     This file is part of glsl4idea.
 *
 *     Glsl4idea is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as
 *     published by the Free Software Foundation, either version 3 of
 *     the License, or (at your option) any later version.
 *
 *     Glsl4idea is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with glsl4idea.  If not, see <http://www.gnu.org/licenses/>.
 */

package glslplugin.lang.elements.expressions;

import com.intellij.lang.ASTNode;
import glslplugin.lang.elements.types.GLSLFunctionType;
import glslplugin.lang.elements.types.GLSLType;
import glslplugin.lang.elements.types.GLSLTypes;
import org.jetbrains.annotations.NotNull;

/**
 * PostfixOperator is ...
 *
 * @author Yngve Devik Hammersland
 *         Date: Jan 28, 2009
 *         Time: 3:17:16 PM
 */
public class GLSLBinaryOperatorExpression extends GLSLOperatorExpression {
    public GLSLBinaryOperatorExpression(@NotNull ASTNode astNode) {
        super(astNode);
    }

    public GLSLExpression getLeftOperand() {
        GLSLExpression[] operands = getOperands();
        if (operands.length == 2) {
            return operands[0];
        } else {
            throw new RuntimeException("Binary operator with " + operands.length + " operand(s).");
        }
    }

    public GLSLExpression getRightOperand() {
        GLSLExpression[] operands = getOperands();
        if (operands.length == 2) {
            return operands[1];
        } else {
            throw new RuntimeException("Binary operator with " + operands.length + " operand(s).");
        }
    }


    public String toString() {
        return "Binary Operator: '" + getOperator().getTextRepresentation() + "'";
    }

    @NotNull
    @Override
    public GLSLType getType() {
        GLSLFunctionType[] alternatives = getOperatorTypeAlternatives();
        if (alternatives.length == 1) {
            return alternatives[0].getBaseType();
        } else {
            return GLSLTypes.UNKNOWN_TYPE;
        }

        /* TODO: REMOVE
        GLSLType leftType = getLeftOperand().getType();
        GLSLType rightType = getRightOperand().getType();

        if (leftType.isConvertibleTo(rightType)) {
            return rightType;
        } else if (rightType.isConvertibleTo(leftType)) {
            return leftType;
        } else {
            return GLSLTypes.UNKNOWN_TYPE;
        }
        */
    }

    public GLSLFunctionType[] getOperatorTypeAlternatives() {
        GLSLOperator operator = getOperator();
        GLSLType leftType = getLeftOperand().getType();
        GLSLType rightType = getRightOperand().getType();

        return operator.getFunctionTypeAlternatives(new GLSLType[]{leftType, rightType});
    }
}