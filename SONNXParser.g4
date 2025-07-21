parser grammar SONNXParser;

options { tokenVocab=SONNXLexer; }

// 根规则：整个S-ONNX模型
model: MODELPROTO LBRACE model_body_def RBRACE;

// ModelProto主体定义
model_body_def:
    ir_version_def
    producer_name_def
    producer_version_def
    domain_def
    model_version_def
    doc_string_def
    graph_def
    opset_import_def
;

// 各部分定义规则
ir_version_def: IR_VERSION EQUALS INTEGER;
producer_name_def: PRODUCER_NAME EQUALS STRING;
producer_version_def: PRODUCER_VERSION EQUALS STRING;
domain_def: DOMAIN EQUALS STRING;
model_version_def: MODEL_VERSION EQUALS INTEGER;
doc_string_def: DOC_STRING EQUALS STRING;

// Graph定义
graph_def: GRAPH LBRACE graph_body_def RBRACE;
graph_body_def:
    name_def
    node_list
    input_list
    output_list
    initializer_list?
;

// Node列表（允许重复节点）
node_list: NODE LBRACE node_def+ RBRACE(node_list)*;
node_def:
    op_type_def
    name_def
    input_arr
    output_arr
    attribute_list?
;

// 输入/输出数组定义
input_arr: INPUT EQUALS LBRACKET STRING (COMMA STRING)* RBRACKET ;
output_arr: OUTPUT EQUALS LBRACKET STRING (COMMA STRING)* RBRACKET ;

// 属性列表（可选）
attribute_list: ATTRIBUTE LBRACE attribute_def+ RBRACE;
attribute_def: name_def value_def;
value_def: VALUE EQUALS (STRING | INTEGER);

// Input/Output列表

input_list: INPUT LBRACE value_info_def+ RBRACE(input_list)*;
output_list: OUTPUT LBRACE value_info_def+ RBRACE(output_list)*;

// Value信息定义
value_info_def:
    name_def
    type_def
;


// 类型定义
type_def: TYPE LBRACE tensor_type_def RBRACE;
tensor_type_def:
    TENSOR_TYPE LBRACE
    elem_type_def
    shape_def
    RBRACE
;

// 元素类型和形状
elem_type_def: ELEM_TYPE EQUALS (INT | FLOAT | STRING_KEY | BOOL);
shape_def: SHAPE LBRACE dim_list RBRACE;

// 维度列表
dim_list: (dim_def)+;
dim_def:
    DIM LBRACE
    (dim_value_def | dim_param_def)
    RBRACE
;
dim_value_def: DIM_VALUE EQUALS INTEGER;
dim_param_def: DIM_PARAM EQUALS STRING;

// Initializer列表
initializer_list: INITIALIZER LBRACE tensor_def+ RBRACE(initializer_list)*;
tensor_def:
    name_def
    data_type_def
    dims_def
    raw_data_def
;

// 张量属性定义
data_type_def: DATA_TYPE EQUALS ( INT | FLOAT | STRING_KEY | BOOL);
dims_def:
    DIMS EQUALS INTEGER+
;
raw_data_def: RAW_DATA EQUALS BYTES;

// Opset导入定义
opset_import_def: OPSET_IMPORT LBRACE domain_def version_def RBRACE;
version_def: VERSION EQUALS INTEGER;

// 通用辅助规则
name_def: NAME EQUALS STRING;
op_type_def: OP_TYPE EQUALS STRING;
