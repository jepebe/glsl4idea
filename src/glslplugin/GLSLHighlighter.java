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

package glslplugin;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import glslplugin.lang.scanner.GLSLFlexAdapter;
import org.jetbrains.annotations.NotNull;

import static glslplugin.lang.elements.GLSLTokenTypes.*;

public class GLSLHighlighter extends SyntaxHighlighterBase {
    static final TextAttributesKey GLSL_NUMBER = TextAttributesKey.createTextAttributesKey("GLSL.NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    //static final TextAttributesKey GLSL_NUMBER = TextAttributesKey.createTextAttributesKey("GLSL.NUMBER", SyntaxHighlighterColors.NUMBER.getDefaultAttributes());

    static final TextAttributesKey GLSL_TYPE_SPECIFIER = TextAttributesKey.createTextAttributesKey("GLSL.TYPE_SPECIFIER", DefaultLanguageHighlighterColors.CLASS_REFERENCE);
    //static final TextAttributesKey GLSL_TYPE_SPECIFIER = TextAttributesKey.createTextAttributesKey("GLSL.TYPE_SPECIFIER", new TextAttributes(new Color(0, 128, 128), null, null, null, Font.BOLD));

    static final TextAttributesKey GLSL_TYPE_QUALIFIERS = TextAttributesKey.createTextAttributesKey("GLSL.QUALIFIER_TOKENS", DefaultLanguageHighlighterColors.KEYWORD);
    //static final TextAttributesKey GLSL_TYPE_QUALIFIERS = TextAttributesKey.createTextAttributesKey("GLSL.QUALIFIER_TOKENS", new TextAttributes(new Color(0, 128, 255), null, null, null, Font.BOLD));

    //static final TextAttributesKey GLSL_PARAMETER_QUALIFIERS = TextAttributesKey.createTextAttributesKey("GLSL.PARAMETER_QUALIFIERS", DefaultLanguageHighlighterColors.KEYWORD);
    //static final TextAttributesKey GLSL_PARAMETER_QUALIFIERS = TextAttributesKey.createTextAttributesKey("GLSL.PARAMETER_QUALIFIERS", new TextAttributes(new Color(128, 255, 0), null, null, null, Font.ITALIC));

    static final TextAttributesKey GLSL_FLOW_KEYWORDS = TextAttributesKey.createTextAttributesKey("GLSL.FLOW_KEYWORDS", DefaultLanguageHighlighterColors.KEYWORD);
    //static final TextAttributesKey GLSL_FLOW_KEYWORDS = TextAttributesKey.createTextAttributesKey("GLSL.FLOW_KEYWORDS", new TextAttributes(new Color(255, 128, 0), null, null, null, Font.PLAIN));

    static final TextAttributesKey GLSL_COMMENT = TextAttributesKey.createTextAttributesKey("GLSL.COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    //static final TextAttributesKey GLSL_COMMENT = TextAttributesKey.createTextAttributesKey("GLSL.COMMENT", SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes());

    static final TextAttributesKey GLSL_IDENTIFIER = TextAttributesKey.createTextAttributesKey("GLSL.IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    //static final TextAttributesKey GLSL_IDENTIFIER = TextAttributesKey.createTextAttributesKey("GLSL.IDENTIFIER", new TextAttributes(Color.BLACK, null, null, null, Font.PLAIN));

    static final TextAttributesKey GLSL_COMPILER_DIRECTIVE = TextAttributesKey.createTextAttributesKey("GLSL.COMPILER_DIRECTIVE",DefaultLanguageHighlighterColors.DOC_COMMENT_MARKUP);
    //new TextAttributes(Color.GREEN.darker().darker(), null, null, null, Font.ITALIC)
    static final TextAttributesKey GLSL_COMPILER_DIRECTIVE_VERSION = TextAttributesKey.createTextAttributesKey("GLSL.COMPILER_DIRECTIVE_VERSION", GLSL_COMPILER_DIRECTIVE);
    static final TextAttributesKey GLSL_COMPILER_DIRECTIVE_EXTENSION = TextAttributesKey.createTextAttributesKey("GLSL.COMPILER_DIRECTIVE_EXTENSION", GLSL_COMPILER_DIRECTIVE);
    static final TextAttributesKey GLSL_COMPILER_DIRECTIVE_PRAGMA = TextAttributesKey.createTextAttributesKey("GLSL.COMPILER_DIRECTIVE_PRAGMA", GLSL_COMPILER_DIRECTIVE);
    static final TextAttributesKey GLSL_COMPILER_DIRECTIVE_OTHER = TextAttributesKey.createTextAttributesKey("GLSL.COMPILER_DIRECTIVE_OTHER", GLSL_COMPILER_DIRECTIVE);
    static final TextAttributesKey GLSL_UNKNOWN = TextAttributesKey.createTextAttributesKey("GLSL.UNKNOWN", HighlighterColors.BAD_CHARACTER);
    //static final TextAttributesKey GLSL_UNKNOWN = TextAttributesKey.createTextAttributesKey("GLSL.UNKNOWN", new TextAttributes(Color.RED, null, null, null, Font.BOLD));

    static final TextAttributesKey GLSL_TEXT = TextAttributesKey.createTextAttributesKey("GLSL.TEXT", DefaultLanguageHighlighterColors.DOT);
    //static final TextAttributesKey GLSL_TEXT = TextAttributesKey.createTextAttributesKey("GLSL.TEXT", new TextAttributes(Color.BLACK, null, null, null, Font.PLAIN));

    public GLSLHighlighter() {
        super();
    }


    @NotNull
    public Lexer getHighlightingLexer() {
        return new GLSLFlexAdapter();
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType type) {
        if (type == INTEGER_CONSTANT || type == FLOAT_CONSTANT || type == BOOL_CONSTANT) {
            return new TextAttributesKey[]{GLSL_NUMBER};
        } else if (type == COMMENT_BLOCK || type == COMMENT_LINE) {
            return new TextAttributesKey[]{GLSL_COMMENT};
        } else if (type == IDENTIFIER) {
            return new TextAttributesKey[]{GLSL_IDENTIFIER};
        } else if (type == COMPILER_DIRECTIVE_VERSION) {
            return new TextAttributesKey[]{GLSL_COMPILER_DIRECTIVE_VERSION};
        } else if (type == COMPILER_DIRECTIVE_EXTENSION) {
            return new TextAttributesKey[]{GLSL_COMPILER_DIRECTIVE_EXTENSION};
        } else if (type == COMPILER_DIRECTIVE_PRAGMA) {
            return new TextAttributesKey[]{GLSL_COMPILER_DIRECTIVE_PRAGMA};
        } else if (type == COMPILER_DIRECTIVE_OTHER) {
            return new TextAttributesKey[]{GLSL_COMPILER_DIRECTIVE_OTHER};
        } else if (TYPE_SPECIFIER_NONARRAY_TOKENS.contains(type)) {
            return new TextAttributesKey[]{GLSL_TYPE_SPECIFIER};
        } else if (QUALIFIER_TOKENS.contains(type)) {
            return new TextAttributesKey[]{GLSL_TYPE_QUALIFIERS};
        } else if (FLOW_KEYWORDS.contains(type)) {
            return new TextAttributesKey[]{GLSL_FLOW_KEYWORDS};
        } else if (type == UNKNOWN) {
            return new TextAttributesKey[]{GLSL_UNKNOWN};
        }
        return new TextAttributesKey[]{GLSL_TEXT};
    }
}
