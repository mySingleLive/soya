package org.soya.codegen;

import org.soya.ast.ClassNode;
import org.soya.ast.MatchBlock;
import org.soya.ast.MatchItem;
import org.soya.ast.PropertyNode;
import org.soya.ast.expr.MatchExpression;

import java.util.Iterator;
import java.util.List;

/**
 * @author: Jun Gong
 */
public class ClassVerfier {

    private ClassNode classNode;

    public void visitClass(ClassNode classNode) {
        this.classNode = classNode;
        visitPattern();
        visitProperties();
    }

    public void visitProperties() {
        List<PropertyNode> propertyList = classNode.getPropertyList();
        for (int i = 0; i < propertyList.size(); i++) {
            PropertyNode propertyNode = propertyList.get(i);
            visitProperty(propertyNode);
        }
    }

    public void visitProperty(PropertyNode propertyNode) {
        if (classNode.containsField(propertyNode.getName())) {
            throw new ClassGeneratorException("Field or Property '" + propertyNode.getName() + "' is already in the scope", propertyNode);
        }
    }

    public void visitPattern() {
        MatchExpression pattern = classNode.getPattern();
        if (pattern == null) {
            return;
        }
        if (!classNode.isPatternClass()) {
            throw new ClassGeneratorException("Pattern definition must be in a pattern class", pattern);
        }

        MatchBlock matchBlock = pattern.getBlock();
        List<MatchItem> items = matchBlock.getItems();
        for (Iterator<MatchItem> iterator = items.iterator(); iterator.hasNext(); ) {
            MatchItem item = iterator.next();
            if (item.getBlock() != null) {
                throw new ClassGeneratorException("Match blocks in the pattern definition are not supported", item);
            }
        }
    }

}
