lexer grammar SONNXLexer;

MODELPROTO: 'ModelProto';
GRAPH: 'graph';
NAME: 'name';
NODE: 'node';
INPUT: 'input';
OUTPUT: 'output';
OP_TYPE: 'op_type';
ATTRIBUTE: 'attribute';
INITIALIZER: 'initializer';
DOC_STRING: 'doc_string';
DOMAIN: 'domain';
MODEL_VERSION: 'model_version';
PRODUCER_NAME: 'producer_name';
PRODUCER_VERSION: 'producer_version';
TYPE: 'type';
TENSOR_TYPE: 'tensor_type';
IR_VERSION: 'ir_version';
ELEM_TYPE: 'elem_type';
SHAPE: 'shape';
DIM: 'dim';
DIMS: 'dims';
RAW_DATA: 'raw_data';
OPSET_IMPORT: 'opset_import';
DIM_VALUE: 'dim_value';
DIM_PARAM:'dim_param';
DATA_TYPE: 'data_type';
VERSION: 'version';
VALUE: 'value';
INT: [iI][nN][tT];
FLOAT: [fF][lL][oO][aA][tT];
STRING_KEY: [sS][tT][rR][iI][nN][gG];
BOOL: [bB][oO][oO][lL];


// 专用符号
LBRACE: '{';
RBRACE: '}';
LBRACKET: '[';
RBRACKET: ']';
COMMA: ',';
EQUALS: '=';

fragment INTEGER_TYPE_SUFFIX: [lL];  // 后缀只能是 l 或 L（小写或大写）

INTEGER: ( '0' | [1-9][0-9]* ) INTEGER_TYPE_SUFFIX?;  // 例如 123L、42l

fragment ESCAPE_SEQUENCE:
    '\\' [btnfr"'\\]  // 匹配 \b, \t, \n, \f, \r, \", \', \\
;

STRING: '"' ( ESCAPE_SEQUENCE | ~[\\"\r\n] )* '"';  // 允许转义字符或非特殊字符

BYTES: [0-9A-Fa-f]+;

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;