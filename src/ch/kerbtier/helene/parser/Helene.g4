grammar Helene;

root            : entity+;

entity          : identifier ':'  type;


type            : string | user | date | integer | list | map;

string          : STRING;

user            : USER;

date            : DATE;

integer         : INTEGER;

list            : '[' type ']';

map             : '{' entity+ '}';


identifier        : IDENTIFIER | STRING | USER | DATE | INTEGER;

STRING            : 'string';

USER              : 'user';

DATE              : 'date';

INTEGER           : 'integer';

IDENTIFIER        : [A-Za-z] ([A-Za-z0-9_])+;

BLOCK_COMMENT     : '/*' .*? '*/' -> skip;
EOL_COMMENT       : '//' ~[\r\n]* -> skip;

WS                : [ \n\t\r]+ -> skip;

